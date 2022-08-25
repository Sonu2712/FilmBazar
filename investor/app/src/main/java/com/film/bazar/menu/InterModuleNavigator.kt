package com.film.bazar.menu

import androidx.appcompat.app.AppCompatActivity
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.home_ui.HomeInterNavigator
import javax.inject.Inject

interface InterModuleNavigator {
    fun openPortfolio()
}

class InterModuleNavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity,
    protected val screenNavigator: ScreenNavigator
) : InterModuleNavigator , HomeInterNavigator {
    override fun openPortfolio() {
    }

    override fun navigateToNotification() {
        screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_NOTIFICATION, true)
    }
}