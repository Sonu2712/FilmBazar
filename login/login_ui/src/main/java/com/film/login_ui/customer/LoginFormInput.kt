package com.film.login_ui.customer

import com.film.login.data.model.LoginFormOutput
import com.film.login_ui.R
import com.film.bazar.appusercore.model.UserType
import com.film.validator.core.Validator
import com.film.validator.data.ValidationResult

data class LoginFormInput(
    @JvmField val userType: UserType,
    @JvmField val userName: String,
    @JvmField val clientCode: String = "",
    @JvmField val password: String
)

object LoginValidator : Validator<LoginFormInput, LoginFormOutput>() {
    override fun execute(input: LoginFormInput): ValidationResult<LoginFormOutput> {
        val errorMessage: Int = when {
            input.userName.isEmpty() -> R.string.error_msg_client_code_mobile_no_email_id_empty
            input.password.isEmpty() -> R.string.msg_password_empty
            else -> -1
        }

        return if (errorMessage != -1) {
            withError(errorMessage)
        } else {
            withData(
                LoginFormOutput(
                    userType = input.userType,
                    userCode = input.userName,
                    clientCode = input.clientCode,
                    password = input.password
                )
            )
        }
    }
}
