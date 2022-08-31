package com.film.bazar.profile

sealed class ProfileUiEvent {
    data class OnItemClicked(
        val id: Int,
        val label : String
    ) : ProfileUiEvent()

    object CallUS : ProfileUiEvent()
    object ChatWithUS : ProfileUiEvent()
    object LogoutConfirmed : ProfileUiEvent()
}