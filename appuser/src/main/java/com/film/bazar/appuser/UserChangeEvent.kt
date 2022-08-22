package com.film.bazar.appuser

import com.film.bazar.appusercore.model.User

interface UserChangeEvent {
    fun onLogin(user: User)

    fun onLogout()
}
