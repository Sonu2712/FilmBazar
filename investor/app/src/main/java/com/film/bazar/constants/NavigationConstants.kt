package com.film.bazar.constants

import androidx.annotation.StringDef
import kotlin.annotation.AnnotationRetention.SOURCE

@kotlin.annotation.Retention(SOURCE)
@StringDef(
    NavigationConstants.NAVIGATE_TO_NONE,
    NavigationConstants.NAVIGATE_TO_LOGOUT,
    NavigationConstants.NAVIGATE_TO_CHANGE_PASSWORD,
    NavigationConstants.NAVIGATE_TO_NOTIFICATION,
    NavigationConstants.NAVIGATE_TO_NOTIFICATION_DETAIL,
    NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_EDIT_PROFILE_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_HELP_SUPPORT_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_PAYMENT_REFUND_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_WRITE_US_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_PAYMENT_DETAILS_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_TERMS_CONDITION_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_SETTINGS_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_PORTFOLIO,
    NavigationConstants.NAVIGATE_TO_WALLET
)
annotation class NavigationConstants {
    companion object {
        const val NAVIGATE_TO_NONE = "none"
        const val NAVIGATE_TO_LOGOUT = "logout"
        const val NAVIGATE_TO_CHANGE_PASSWORD = "changepassword"
        const val NAVIGATE_TO_URL = "url"
        const val NAVIGATE_TO_HOME = "home"
        const val NAVIGATE_TO_MOVIE_DETAIL = "mvoiedetail"

        const val NAVIGATE_TO_PORTFOLIO = "portfolio"
        const val NAVIGATE_TO_WALLET = "wallet"
        const val NAVIGATE_TO_NOTIFICATION_DETAIL = "notificationdetail"
        const val NAVIGATE_TO_NOTIFICATION = "notification"
        const val NAVIGATE_TO_SETTINGS_FRAGMENT = "settings"

        const val NAVIGATE_TO_PROFILE_FRAGMENT = "profile"
        const val NAVIGATE_TO_EDIT_PROFILE_FRAGMENT = "editprofile"

        const val NAVIGATE_TO_HELP_SUPPORT_FRAGMENT = "helpsupport"
        const val NAVIGATE_TO_PAYMENT_REFUND_FRAGMENT = "helppaymentrefund"
        const val NAVIGATE_TO_WRITE_US_FRAGMENT = "writeus"

        const val NAVIGATE_TO_TERMS_CONDITION_FRAGMENT = "termscondition"
        const val NAVIGATE_TO_PAYMENT_DETAILS_FRAGMENT = "paymentdetails"
    }

    annotation class NavigationPageIdentifier{
        companion object{
            const val PAGE_IDENTIFIER_MARKETS = "markets"
            const val PAGE_IDENTIFIER_INVEST_NOW = "investnow"
            const val PAGE_IDENTIFIER_REPORTS = "reports"
            const val PAGE_IDENTIFIER_LEARN = "learn"
            const val PAGE_IDENTIFIER_OTHER_ESSENTIALS = "otheressentials"
        }
    }
}