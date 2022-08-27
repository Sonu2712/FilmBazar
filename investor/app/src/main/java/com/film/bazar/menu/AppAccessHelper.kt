package com.film.bazar.menu

import com.film.login_ui.nav.LoginConstants
import com.film.bazar.appuser.helper.PageAccessHelper
import com.film.bazar.constants.NavigationConstants

object AppAccessHelper : PageAccessHelper() {
    override fun isAccessibleToTradingUsers(pageId: String): Boolean {
        return isAccessibleToNonTradingUsers(pageId)
    }

    override fun isAccessibleToNonTradingUsers(pageId: String): Boolean {
        return when (pageId) {
            NavigationConstants.NAVIGATE_TO_CHANGE_PASSWORD -> true
            NavigationConstants.NAVIGATE_TO_NOTIFICATION -> true
            NavigationConstants.NAVIGATE_TO_NOTIFICATION_DETAIL -> true
            NavigationConstants.NAVIGATE_TO_HOME -> true
            NavigationConstants.NAVIGATE_TO_MOVIE_DETAIL -> true
            NavigationConstants.NAVIGATE_TO_SETTINGS_FRAGMENT -> true

            NavigationConstants.NAVIGATE_TO_PORTFOLIO -> true
            NavigationConstants.NAVIGATE_TO_WALLET -> true

            NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT -> true
            NavigationConstants.NAVIGATE_TO_EDIT_PROFILE_FRAGMENT -> true
            NavigationConstants.NAVIGATE_TO_HELP_SUPPORT_FRAGMENT -> true
            NavigationConstants.NAVIGATE_TO_TERMS_CONDITION_FRAGMENT -> true
            NavigationConstants.NAVIGATE_TO_PAYMENT_DETAILS_FRAGMENT -> true
            else -> isAccessibleToGuestUsers(pageId)
        }
    }

    override fun isAccessibleToMutualFundUsers(pageId: String): Boolean {
        return when (pageId) {
            NavigationConstants.NAVIGATE_TO_CHANGE_PASSWORD -> true
            NavigationConstants.NAVIGATE_TO_NOTIFICATION -> true
            NavigationConstants.NAVIGATE_TO_NOTIFICATION_DETAIL -> true
            NavigationConstants.NAVIGATE_TO_HOME -> true
            NavigationConstants.NAVIGATE_TO_MOVIE_DETAIL -> true
            NavigationConstants.NAVIGATE_TO_SETTINGS_FRAGMENT -> true
            NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT -> true
            else -> isAccessibleToGuestUsers(pageId)
        }
    }

    override fun isAccessibleToGuestUsers(pageId: String): Boolean {
        return when (pageId) {
            NavigationConstants.NAVIGATE_TO_LOGOUT -> true
            else -> isAccessibleToOpenUsers(pageId)
        }
    }

    override fun isAccessibleToOpenUsers(pageId: String): Boolean {
        return when (pageId) {
            LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT -> true
            NavigationConstants.NAVIGATE_TO_CHANGE_PASSWORD -> true
            else -> false
        }
    }
}