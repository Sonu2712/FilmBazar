package com.film.login_ui.base

import com.film.login_ui.LoginType

data class LoginPageDataModel(
    val key: String,
    val value: String,
    val type: LoginType,
    val visible: Boolean,
    val showInfo: Boolean = false
) {
    companion object {
        val EMPTY = LoginPageDataModel("", "", LoginType.Customer(), false, false)
    }
}
