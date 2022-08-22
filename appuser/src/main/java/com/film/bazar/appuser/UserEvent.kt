package com.film.bazar.appuser

import com.film.bazar.appusercore.model.User

sealed class UserEvent {
    object OpenUser : UserEvent()
    data class LoggedIn(val user: User) : UserEvent()
    object LoggedOut : UserEvent()
}
