package com.film.bazar.menu

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.home_ui.HomeFragment
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.home_ui.detail.MovieDetailFragment
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.portfolio.PortfolioFragment
import com.film.bazar.profile.ProfileFragment
import com.film.bazar.profile.editprofile.EditProfileFragment
import com.film.bazar.profile.helpsupport.HelpSupportFragment
import com.film.bazar.profile.helpsupport.paymentrefund.PaymentRefundFragment
import com.film.bazar.profile.paymentdetails.PaymentDetailsFragment
import com.film.bazar.profile.termscondition.TermsConditionFragment
import com.film.bazar.wallet.WalletFragment

object MenuGetter {

    @SuppressLint("SwitchIntDef")
    fun getFragment(@NavigationConstants menuCode: String): Fragment? {
        return when (menuCode) {
            NavigationConstants.NAVIGATE_TO_HOME -> HomeFragment()
            NavigationConstants.NAVIGATE_TO_MOVIE_DETAIL -> MovieDetailFragment()
            NavigationConstants.NAVIGATE_TO_NOTIFICATION -> NotificationFragment()
            NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT -> ProfileFragment()
            NavigationConstants.NAVIGATE_TO_EDIT_PROFILE_FRAGMENT -> EditProfileFragment()
            NavigationConstants.NAVIGATE_TO_HELP_SUPPORT_FRAGMENT -> HelpSupportFragment()
            NavigationConstants.NAVIGATE_TO_PAYMENT_REFUND_FRAGMENT -> PaymentRefundFragment()
            NavigationConstants.NAVIGATE_TO_TERMS_CONDITION_FRAGMENT -> TermsConditionFragment()
            NavigationConstants.NAVIGATE_TO_PAYMENT_DETAILS_FRAGMENT -> PaymentDetailsFragment()
            NavigationConstants.NAVIGATE_TO_PORTFOLIO -> PortfolioFragment()
            NavigationConstants.NAVIGATE_TO_WALLET -> WalletFragment()
            else -> null
        }
    }

    fun isActivity(pageId: String): Boolean {
        return false
    }

    fun isCustomNavigation(pageId: String): Boolean {
        return false
    }
}

fun UserType.isAccessible(pageId: String): Boolean {
    return AppAccessHelper.isAccessible(this, pageId)
}
