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
    NavigationConstants.NAVIGATE_TO_SETTINGS_FRAGMENT,
    NavigationConstants.NAVIGATE_TO_PORTFOLIO,
    NavigationConstants.NAVIGATE_TO_WALLET,
    NavigationConstants.NAVIGATE_TO_MORE
)
annotation class NavigationConstants {
    companion object {
        const val NAVIGATE_TO_NONE = "none"
        const val NAVIGATE_TO_LOGOUT = "logout"
        const val NAVIGATE_TO_CHANGE_PASSWORD = "changepassword"
        const val NAVIGATE_TO_URL = "url"
        const val NAVIGATE_TO_HOME = "home"
        const val NAVIGATE_TO_PORTFOLIO = "portfolio"
        const val NAVIGATE_TO_WALLET = "wallet"
        const val NAVIGATE_TO_MORE = "more"
        const val NAVIGATE_TO_NOTIFICATION_DETAIL = "notificationdetail"
        const val NAVIGATE_TO_NOTIFICATION = "notification"
        const val NAVIGATE_TO_PROFILE_FRAGMENT = "profile"
        const val NAVIGATE_TO_SETTINGS_FRAGMENT = "settings"
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