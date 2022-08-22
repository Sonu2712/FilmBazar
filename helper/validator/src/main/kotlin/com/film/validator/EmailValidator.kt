package com.film.validator

import com.film.validator.core.Validator
import com.film.validator.data.ValidationResult
import java.util.regex.Pattern

object EmailValidator : Validator<String, String>() {
  @JvmStatic
  val EMAIL_VALIDATOR: Pattern =
      Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$")

  override fun execute(input: String): ValidationResult<String> {
    var message: String? = null
    if (input.isEmpty()) {
      message = "Please enter an Email ID"
    } else if (!validateEmail(input)) {
      message = "Please enter a valid Email ID"
    }
    return if (message != null) {
      failure(message)
    } else {
      success(input)
    }
  }

  @JvmStatic
  fun validateEmail(input: String): Boolean {
    val matcher = EMAIL_VALIDATOR.matcher(input)
    return matcher.matches()
  }
}