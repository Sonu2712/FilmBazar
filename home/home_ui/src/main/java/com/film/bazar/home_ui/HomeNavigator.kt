package com.film.bazar.home_ui

import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.navigator.UrlNavigator
import javax.inject.Inject

interface HomeNavigator {
    fun navigateToNotification()
    fun openMovieDetail(id : Int, tabType :String)
    fun playVideo(videoId : String)
}

interface HomeInterNavigator{
    fun navigateToNotification()
    fun openMovieDetail(id: Int, tabType: String)
    fun playVideo(videoId: String)
}

class HomeNavigatorImpl @Inject constructor(
    protected val interNavigator: HomeInterNavigator,
    val urlNavigator: UrlNavigator
) : HomeNavigator {
    override fun navigateToNotification() {
        interNavigator.navigateToNotification()
    }

    override fun openMovieDetail(id: Int, tabType: String) {
        interNavigator.openMovieDetail(id, tabType)
    }

    override fun playVideo(videoId: String) {
        interNavigator.playVideo(videoId)
    }
}