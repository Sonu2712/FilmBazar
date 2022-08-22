package com.film.bazar.home_ui

import com.film.app.core.base.BaseView
import com.film.bazar.home_domain.MovieData
import com.film.bazar.home_domain.MovieInfo
import com.film.commons.data.UiModel

interface HomeView : BaseView {
    fun renderWelcome(uiModel: UiModel<MovieData>)
}