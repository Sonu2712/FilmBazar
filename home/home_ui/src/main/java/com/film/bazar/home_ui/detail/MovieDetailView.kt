package com.film.bazar.home_ui.detail

import com.film.app.core.base.BaseView
import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_domain.MovieDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface MovieDetailView : BaseView {
    fun openCastCrew() : Observable<HomeUiEvent.OpenCastCrew>
    fun renderCarsCrew(uiModel: UiModel<CastCrewDetail>)
    fun onNavigationEvent(): Observable<HomeUiEvent.NavigationEvent>
    fun render(uiModel: UiModel<MovieDetail>, tabType : String)
    fun onBackClicked() : Observable<HomeUiEvent.GoBack>

    companion object{
        const val ARG_MOVIE_ID = "movieid"
        const val ARG_MOVIE_TYPE = "movietype"
    }
}