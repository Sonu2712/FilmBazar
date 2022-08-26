package com.film.bazar.profile

sealed class ProfileEvent {
    object LogoutConfirmed : ProfileEvent()
}