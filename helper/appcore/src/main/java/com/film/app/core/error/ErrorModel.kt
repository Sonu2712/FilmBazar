package com.film.app.core.error

import android.content.Context
import androidx.annotation.StringRes
import com.film.app.core.error.ErrorState.NO_DATA_ERROR

class ErrorModel internal constructor(
    val messageId: Int,
    val message: String?,
    @JvmField val reason: ErrorState
) {

  constructor(@StringRes messageId: Int, reason: ErrorState) :
      this(messageId = messageId, message = null, reason = reason)

  constructor(message: String, reason: ErrorState) :
      this(messageId = 0, message = message, reason = reason)

  fun getResolvedMessage(context: Context): String {
    return message ?: context.getString(messageId)
  }

  companion object {
    @JvmField
    val NoDataAvailable = ErrorModel(NO_DATA_ERROR.messageStr, NO_DATA_ERROR)

    @JvmStatic
    fun noData(@StringRes messageId: Int): ErrorModel {
      return ErrorModel(messageId, NO_DATA_ERROR)
    }

    @JvmStatic
    fun noData(message: String): ErrorModel {
      return ErrorModel(message, NO_DATA_ERROR)
    }

    @JvmStatic
    fun noDataAvailable(): ErrorModel {
      return NoDataAvailable
    }
  }
}
