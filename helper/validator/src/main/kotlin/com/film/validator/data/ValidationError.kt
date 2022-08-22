package com.film.validator.data

import androidx.annotation.StringRes

class ValidationError internal constructor(
    @StringRes @JvmField val error: Int?,
    @JvmField val message: String?,
    @JvmField val identifier: Any? = null
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ValidationError

    if (error != other.error) return false
    if (message != other.message) return false
    if (identifier != other.identifier) return false

    return true
  }

  override fun hashCode(): Int {
    var result = error ?: 0
    result = 31 * result + (message?.hashCode() ?: 0)
    result = 31 * result + (identifier?.hashCode() ?: 0)
    return result
  }

  override fun toString(): String {
    return "ValidationError(error=$error, message=$message, identifier=$identifier)"
  }

  companion object {
    fun withError(
        @StringRes error: Int,
        identifier: Any? = null
    ): ValidationError {
      return ValidationError(error, null, identifier);
    }

    fun withMessage(
        message: String,
        identifier: Any? = null
    ): ValidationError {
      return ValidationError(null, message, identifier);
    }
  }
}