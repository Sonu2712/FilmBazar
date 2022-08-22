package com.film.login_ui.nav

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.SOURCE)
@StringDef(
    LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN,
    LoginConstants.NAVIGATE_TO_GUEST_LOGIN,
    LoginConstants.NAVIGATE_TO_FORGOT_PASSWORD,
    LoginConstants.NAVIGATE_TO_LOGIN_BASE,
    LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT
)
annotation class LoginConstants {
    companion object {
        const val NAVIGATE_TO_LOGIN_BASE = "LoginBase"
        const val NAVIGATE_TO_CUSTOMER_LOGIN = "customerlogin"
        const val NAVIGATE_TO_GUEST_LOGIN = "guestlogin"
        const val NAVIGATE_TO_FORGOT_PASSWORD = "forgotpassword"
        const val NAVIGATE_TO_OPEN_ACCOUNT = "openaccount"
        const val NAVIGATE_TO_EKYC_HELPER = "ekyc"
    }
}
