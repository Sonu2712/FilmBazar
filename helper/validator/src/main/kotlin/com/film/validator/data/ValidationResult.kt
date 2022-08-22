package com.film.validator.data

sealed class ValidationResult<out T> {
  data class Success<out T>(@JvmField val data: T) : ValidationResult<T>()
  data class Failure<out T>(@JvmField val error: ValidationError) : ValidationResult<T>()

  companion object {
    internal fun <T> success(data: T): ValidationResult<T> {
      return Success(data)
    }

    internal fun <T> failure(
        error: Int,
        identifier: Any? = null
    ): ValidationResult<T> {
      return Failure(ValidationError.withError(error, identifier))
    }

    internal fun <T> failure(
        message: String,
        identifier: Any? = null
    ): ValidationResult<T> {
      return Failure(ValidationError.withMessage(message, identifier))
    }
  }
}