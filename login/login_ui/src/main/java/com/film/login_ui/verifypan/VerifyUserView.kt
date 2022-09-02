package com.film.login_ui.verifypan

sealed class LoginUiEvent {
    object Authenticated : LoginUiEvent()
    object OnResume : LoginUiEvent()
    object LoginSubmit : LoginUiEvent()
    object LoginRequestOtp : LoginUiEvent()
    object LoginVerifyOtp : LoginUiEvent()
}
