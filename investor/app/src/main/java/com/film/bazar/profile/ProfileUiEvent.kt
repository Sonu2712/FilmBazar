package com.film.bazar.profile

sealed class ProfileUiEvent {
    data class OnItemClicked(
        val id: Int,
        val label : String
    ) : ProfileUiEvent()
    object LogoutConfirmed : ProfileUiEvent()
}