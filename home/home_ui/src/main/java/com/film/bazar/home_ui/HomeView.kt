package com.film.bazar.home_ui

import com.film.app.core.base.BaseView
import com.film.bazar.home_domain.MovieData
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_domain.MovieTab
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface HomeView : BaseView {
    fun onFilterClicked() : Observable<HomeUiEvent.OpenSortFilterBottomSheet>
    fun showSortFilterBottomSheet(tab: MovieTab)
    fun onNavigationEvent(): Observable<HomeUiEvent.NavigationEvent>

    fun onMovieInfoClicked() : Observable<HomeUiEvent.MovieDetail>
    fun getSelectedMovieTab(): MovieTab?

    fun renderWelcome(uiModel: UiModel<MovieData>)
}