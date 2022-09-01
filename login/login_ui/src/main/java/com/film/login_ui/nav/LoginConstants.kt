package com.film.login_ui.nav

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.SOURCE)
@StringDef(
    LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN,
    LoginConstants.NAVIGATE_TO_LOGIN_BASE,
    LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT
)
annotation class LoginConstants {
    companion object {
        const val NAVIGATE_TO_LOGIN_BASE = "LoginBase"
        const val NAVIGATE_TO_CUSTOMER_LOGIN = "customerlogin"
        const val NAVIGATE_TO_OPEN_ACCOUNT = "openaccount"
    }
}
