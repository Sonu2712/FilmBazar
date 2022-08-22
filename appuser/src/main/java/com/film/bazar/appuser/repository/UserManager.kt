package com.film.bazar.appuser.repository

import com.film.bazar.appuser.UserEvent
import com.film.bazar.appuser.model.LoginInfo
import com.film.bazar.appusercore.model.User
import com.film.bazar.appusercore.model.UserType
import io.reactivex.rxjava3.core.Observable

interface UserManager {

    fun dataStore(): UserDataStore

    fun onUserChanged(): Observable<UserEvent>

    fun getCredentials(): LoginInfo?

    fun getUser(): User?

    fun getUserType(): UserType

    fun isLoggedIn(): Boolean
}