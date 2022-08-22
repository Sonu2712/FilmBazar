package com.film.validator

import com.film.validator.core.Validator
import com.film.validator.data.ValidationResult
import java.util.regex.Pattern

object MobileNoValidator : Validator<String, String>() {
  @JvmStatic
  val MobileNo_VALIDATOR: Pattern = Pattern.compile("[6-9][0-9]{9}")

  override fun execute(input: String): ValidationResult<String> {
    var message: String? = null
    if (input.isEmpty()) {
      message = "Please enter the Mobile Number"
    } else if (!validateMobileNo(input)) {
      message = "Please enter the valid Mobile Number"
    }
    return if (message != null) {
      failure(message)
    } else {
      success(input)
    }
  }

  @JvmStatic
  fun validateMobileNo(input: String): Boolean {
    val matcher = MobileNo_VALIDATOR.matcher(input)
    return matcher.matches()
  }
}