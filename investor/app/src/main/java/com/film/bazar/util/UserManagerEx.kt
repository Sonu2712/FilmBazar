package com.film.bazar.util

import com.film.bazar.appuser.UserEvent
import com.film.bazar.appuser.repository.UserManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

fun UserManager.upcomingUserChanges(scheduler: Scheduler): Observable<UserEvent> {
    return onUserChanged()
        .distinctUntilChanged()
        .skip(1)
        .observeOn(scheduler)
}
