package com.film.app.core.error

import androidx.annotation.StringRes
import com.film.app.core.R

enum class ErrorState(@JvmField @param:StringRes val messageStr: Int) {
  NETWORK_ERROR(R.string.network_failure),
  SERVICE_ERROR(R.string.service_failure),
  VALIDATION_ERROR(R.string.error_message_validation_failed),
  NO_DATA_ERROR(R.string.error_message_no_data_available),
  UNKNOWN_ERROR(R.string.unknown_failure);
}