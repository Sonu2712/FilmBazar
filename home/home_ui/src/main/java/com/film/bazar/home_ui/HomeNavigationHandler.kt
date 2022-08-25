package com.film.bazar.home_ui

import javax.inject.Inject

class HomeNavigationHandler @Inject constructor(
    private val navigator: HomeNavigator
) {
    fun handle(event: HomeNavEvent) {
        return when (event) {
            is HomeNavEvent.NotificationEvent -> {
                navigator.navigateToNotification()
            }
        }
    }
}