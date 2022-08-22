package com.film.validator

import com.film.validator.core.Validator
import com.film.validator.data.ValidationResult
import java.util.regex.Pattern

object PanValidator : Validator<String, String>() {
  @JvmStatic
  val PAN_VALIDATOR: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

  override fun execute(input: String): ValidationResult<String> {
    var message: String? = null
    if (input.isEmpty()) {
      message = "Please enter PAN Number"
    } else if (!validatePan(input)) {
      message = "Please enter a valid PAN Number"
    }
    return if (message != null) {
      failure(message)
    } else {
      success(input)
    }
  }

  @JvmStatic
  fun validatePan(input: String): Boolean {
    val matcher = PAN_VALIDATOR.matcher(input)
    return matcher.matches()
  }
}