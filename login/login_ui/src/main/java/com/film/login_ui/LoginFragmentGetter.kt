package com.film.login_ui

import com.film.bazar.coreui.appcoreui.base.BaseFragment
import com.film.login_ui.base.LoginBaseFragment
import com.film.login_ui.customer.CustomerLoginFragment
import com.film.login_ui.nav.LoginConstants

object LoginFragmentGetter {
    @JvmStatic
    fun getFragment(@LoginConstants menuCode: String): BaseFragment? {
        return when (menuCode) {
            LoginConstants.NAVIGATE_TO_LOGIN_BASE -> LoginBaseFragment()
            LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN -> CustomerLoginFragment()
            else -> null
        }
    }

    @JvmStatic
    fun isHandled(@LoginConstants pageId: String): Boolean {
        return when (pageId) {
            LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN -> true
            LoginConstants.NAVIGATE_TO_GUEST_LOGIN -> true
            LoginConstants.NAVIGATE_TO_FORGOT_PASSWORD -> true
            LoginConstants.NAVIGATE_TO_LOGIN_BASE -> true
            LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT -> true
            else -> false
        }
    }
}