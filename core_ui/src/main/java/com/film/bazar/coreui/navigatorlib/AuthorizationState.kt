package com.film.bazar.coreui.navigatorlib

sealed class AuthorizationState {
  object OK : AuthorizationState()
  object CANCEL : AuthorizationState()
}