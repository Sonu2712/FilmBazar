package com.film.bazar.home_ui

import io.reactivex.rxjava3.subjects.PublishSubject

sealed class HomeUiEvent {
    object OpenSortFilterBottomSheet : HomeUiEvent()
    data class NavigationEvent(val event: HomeNavEvent) : HomeUiEvent()
}

sealed class HomeNavEvent {
    object NotificationEvent : HomeNavEvent()
}

fun PublishSubject<HomeUiEvent>.onNext(navEvent: HomeNavEvent) {
    onNext(HomeUiEvent.NavigationEvent(navEvent))
}