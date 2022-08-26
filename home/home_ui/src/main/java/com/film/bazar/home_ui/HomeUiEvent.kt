package com.film.bazar.home_ui

import io.reactivex.rxjava3.subjects.PublishSubject

sealed class HomeUiEvent {
    object OpenSortFilterBottomSheet : HomeUiEvent()
    data class NavigationEvent(val event: HomeNavEvent) : HomeUiEvent()

    data class MovieDetail(
        @JvmField val id : Int,
        @JvmField val tabType : String
    ): HomeUiEvent()

    data class OpenCastCrew(
        val id: Int
    ) : HomeUiEvent()
    data class PlayVideo(val videoId: String) : HomeUiEvent()
}

sealed class HomeNavEvent {
    object NotificationEvent : HomeNavEvent()
    data class OpenVideo(val videoId : String) : HomeNavEvent()
}

fun PublishSubject<HomeUiEvent>.onNext(navEvent: HomeNavEvent) {
    onNext(HomeUiEvent.NavigationEvent(navEvent))
}