package com.film.login.data

import com.film.login.data.repository.LoginRepository
import io.reactivex.rxjava3.core.Observable

fun LoginRepository.logout(): Observable<Boolean> {
    return logoutUser()
        .toSingleDefault(true)
        .toObservable()
}
