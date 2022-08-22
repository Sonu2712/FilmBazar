package com.film.validator.exception

import androidx.annotation.StringRes
import com.film.validator.data.ValidationError

class ValidationException(
    @JvmField val error: ValidationError
) : Exception() {
  override fun toString(): String {
    return "ValidationException(error=$error)"
  }
}

inline fun validationFailure(
    @StringRes error: Int,
    identifier: Any? = null
): Nothing {
  throw ValidationException(ValidationError.withError(error, identifier))
}

inline fun validationFailure(
    message: String,
    identifier: Any? = null
): Nothing {
  throw ValidationException(ValidationError.withMessage(message, identifier))
}