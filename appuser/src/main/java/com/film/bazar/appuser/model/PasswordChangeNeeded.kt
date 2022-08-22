package com.film.bazar.appuser.model

data class PasswordChangeNeeded(
    @JvmField val passwordExpired: Boolean,
    @JvmField val firstTimeLogin: Boolean
)
