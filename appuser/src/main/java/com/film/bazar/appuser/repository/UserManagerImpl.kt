package com.film.bazar.appuser.repository

import com.jakewharton.rxrelay3.BehaviorRelay
import com.film.bazar.appuser.UserEvent
import com.film.bazar.appuser.model.LoginInfo
import com.film.bazar.appusercore.model.User
import com.film.bazar.appusercore.model.UserType
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManagerImpl @Inject internal constructor(
    private val userCache: UserCache
) : UserManager {
    private var user: User?
    private val userChangeSubject: BehaviorRelay<UserEvent>

    init {
        val user = userCache.getUser()
        this.user = user
        userChangeSubject = BehaviorRelay.createDefault(
            if (user != null) UserEvent.LoggedIn(user) else UserEvent.OpenUser
        )
    }

    override fun dataStore(): UserDataStore {
        return userCache.dataStore()
    }

    fun saveUser(user: User) {
        userCache.saveUser(user)
        this.user = user
        userChangeSubject.accept(UserEvent.LoggedIn(user))
    }

    fun saveUserSettings(user: User) {
        userCache.saveUser(user)
        this.user = user
    }

    fun saveCredentials(loginInfo: LoginInfo) {
        userCache.saveCredentials(loginInfo)
    }

    fun logout() {
        this.user = null
        userCache.clearUser()
        userChangeSubject.accept(UserEvent.LoggedOut)
    }

    override fun onUserChanged(): Observable<UserEvent> {
        return userChangeSubject.hide()
    }

    override fun getCredentials(): LoginInfo? {
        return userCache.getCredentials()
    }

    override fun getUser(): User? {
        return user
    }

    override fun getUserType(): UserType {
        return user?.userType ?: UserType.OPEN_USER
    }

    override fun isLoggedIn(): Boolean {
        return getUserType() != UserType.OPEN_USER
    }
}