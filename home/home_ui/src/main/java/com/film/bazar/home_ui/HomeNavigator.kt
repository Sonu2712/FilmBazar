package com.film.bazar.home_ui

import com.film.bazar.coreui.navigator.UrlNavigator
import javax.inject.Inject

interface HomeNavigator {
    fun navigateToNotification()
}

interface HomeInterNavigator{
    fun navigateToNotification()
}

class HomeNavigatorImpl @Inject constructor(
    protected val interNavigator: HomeInterNavigator,
    val urlNavigator: UrlNavigator
) : HomeNavigator {
    override fun navigateToNotification() {
        interNavigator.navigateToNotification()
    }
}