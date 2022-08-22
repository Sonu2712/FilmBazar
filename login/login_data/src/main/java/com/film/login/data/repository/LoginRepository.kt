package com.film.login.data.repository

import com.film.login.data.model.LoginFormOutput
import com.film.login.data.model.LoginResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface LoginRepository {
    fun login(
        loginFormOutput: LoginFormOutput
    ): Observable<LoginResponse>

    fun logoutUser(): Completable
}
