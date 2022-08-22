package com.film.login.data.repository

import com.film.app.core.prefs.StringPreference
import com.film.commons.utils.Clock
import com.film.login.data.model.LoginFormOutput
import com.film.login.data.model.LoginResponse
import com.film.login.data.transformNormalLoginLocal
import com.film.token.manager.data.JwtTokens
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class AppUserRepositoryImpl @Inject internal constructor(
    val clock: Clock,
    @com.film.annotations.AppMenu val appMenuPref: StringPreference
) : AppUserRepository {
    override fun login(request: LoginFormOutput): Observable<LoginResponse> {
        return Observable.just(transformNormalLoginLocal(clock.getTimestamp()))
    }

    override fun logout(request: JwtTokens): Observable<Boolean> {
        return Observable.just(true)
    }
}
