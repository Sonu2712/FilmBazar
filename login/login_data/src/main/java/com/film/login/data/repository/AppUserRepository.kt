package com.film.login.data.repository

import com.film.login.data.model.LoginFormOutput
import com.film.login.data.model.LoginResponse
import com.film.token.manager.data.JwtTokens
import io.reactivex.rxjava3.core.Observable

interface AppUserRepository {
    fun login(request: LoginFormOutput): Observable<LoginResponse>

    fun logout(request: JwtTokens): Observable<Boolean>
}