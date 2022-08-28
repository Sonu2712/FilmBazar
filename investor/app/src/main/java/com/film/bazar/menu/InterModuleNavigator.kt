package com.film.bazar.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.home_ui.HomeInterNavigator
import com.film.bazar.home_ui.detail.MovieDetailView
import com.film.bazar.video.VideoNavigator
import com.film.bazar.video.VideoPlayActivity
import javax.inject.Inject

interface InterModuleNavigator {
    fun openPortfolio()
}

class InterModuleNavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity,
    protected val screenNavigator: ScreenNavigator,
    private val videoNavigator: VideoNavigator
) : InterModuleNavigator , HomeInterNavigator {
    override fun openPortfolio() {
    }

    override fun navigateToNotification() {
        screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_NOTIFICATION, true)
    }

    override fun openMovieDetail(id: Int, tabType: String) {
        val bundle = Bundle().apply {
            putInt(MovieDetailView.ARG_MOVIE_ID, id)
            putString(MovieDetailView.ARG_MOVIE_TYPE, tabType)
        }
        screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_MOVIE_DETAIL, bundle, true)
    }

    override fun playVideo(videoId: String) {
        videoNavigator.playVideo(videoId)
    }
}