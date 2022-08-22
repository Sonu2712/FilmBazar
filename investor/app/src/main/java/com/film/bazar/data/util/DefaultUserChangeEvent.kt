package com.film.bazar.data.util

import com.film.annotations.UserIdentity
import com.film.app.core.prefs.StringPreference
import com.film.bazar.appuser.UserChangeEvent
import com.film.bazar.appusercore.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserChangeEvent @Inject constructor(
    @UserIdentity private val userIdentity: StringPreference
) : UserChangeEvent {

    override fun onLogin(user: User) {
        //crashlytics.setUserId(userIdentity.get())
    }

    override fun onLogout() {
        //crashlytics.setUserId(userIdentity.get())
    }
}
