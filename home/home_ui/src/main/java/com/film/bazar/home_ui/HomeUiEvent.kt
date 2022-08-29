package com.film.bazar.home_ui

import com.film.bazar.home_domain.MovieFilter
import com.film.bazar.home_domain.MovieTab
import io.reactivex.rxjava3.subjects.PublishSubject

sealed class HomeUiEvent {
    object GoBack : HomeUiEvent()
    object ResetSortFilter : HomeUiEvent()
    object OpenSortFilterBottomSheet : HomeUiEvent()
    data class FilterApplied(val filter: MovieFilter) : HomeUiEvent()
    data class NavigationEvent(val event: HomeNavEvent) : HomeUiEvent()

    data class MovieDetail(
        @JvmField val id : Int,
        @JvmField val tabType : String
    ): HomeUiEvent()

    data class MovieTabChanged(val tab: MovieTab) : HomeUiEvent()
}

sealed class HomeNavEvent {
    object NotificationEvent : HomeNavEvent()
    data class OpenVideo(val videoId : String) : HomeNavEvent()
}

fun PublishSubject<HomeUiEvent>.onNext(navEvent: HomeNavEvent) {
    onNext(HomeUiEvent.NavigationEvent(navEvent))
}