package com.film.bazar.menu

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.home_ui.HomeFragment
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.home_ui.detail.MovieDetailFragment
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.profile.ProfileFragment

object MenuGetter {

    @SuppressLint("SwitchIntDef")
    fun getFragment(@NavigationConstants menuCode: String): Fragment? {
        return when (menuCode) {
            NavigationConstants.NAVIGATE_TO_HOME -> HomeFragment()
            NavigationConstants.NAVIGATE_TO_MOVIE_DETAIL -> MovieDetailFragment()
            NavigationConstants.NAVIGATE_TO_NOTIFICATION -> NotificationFragment()
            NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT -> ProfileFragment()
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
