@file:JvmName("ErrorUtils")

package com.film.app.core.error

import com.film.app.core.R
import com.film.app.core.error.ErrorState.NETWORK_ERROR
import com.film.app.core.error.ErrorState.NO_DATA_ERROR
import com.film.app.core.error.ErrorState.SERVICE_ERROR
import com.film.app.core.error.ErrorState.UNKNOWN_ERROR
import com.film.app.core.error.ErrorState.VALIDATION_ERROR
import com.film.app.core.exception.DataException
import com.film.app.core.exception.ServiceFailureException
import com.film.app.core.exception.WebCallException
import com.film.validator.exception.ValidationException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * This method logs failure and parses Throwable type to return
 * a messageId and reason wrapped in ErrorModel.
 *
 * @param this@getErrorOnFailure the error that occurred during API call.
 * @return ErrorModel for the inputs.
 */
fun Throwable.getErrorOnFailure(): ErrorModel {
  if (this is ValidationException) {
    return getErrorModel()
  }
  Timber.e(this)
  val reason = getReason()
  val message: String? = when (this) {
    is ServiceFailureException -> errorMessage
    is WebCallException -> errorMessage
    is DataException -> msg
    else -> null
  }
  return if (message != null) {
    ErrorModel(message, reason)
  } else {
    ErrorModel(reason.messageStr, reason)
  }
}

fun ValidationException.getErrorModel(): ErrorModel {
  return error.message?.let {
    ErrorModel(it, VALIDATION_ERROR)
  } ?: ErrorModel(error.error ?: R.string.error_message_validation_failed, VALIDATION_ERROR)
}

/**
 * It parses the throwable to check for the failure
 * condition that occurred during the request.
 *
 * @param this@checkException Error throwable received in onFailure Callback.
 * @return a Constant of ErrorState
 */
fun Throwable.getReason(): ErrorState {
  return if (this is UnknownHostException || this is ConnectException || this is SocketException) {
    NETWORK_ERROR
  } else if (this is SocketTimeoutException) {
    SERVICE_ERROR
  } else if (this is ServiceFailureException
      || this is WebCallException
      || this is DataException) {
    NO_DATA_ERROR
  } else {
    UNKNOWN_ERROR
  }
}