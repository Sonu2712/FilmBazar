package com.film.login_ui.customer

import com.film.app.core.base.BaseView
import com.film.commons.data.UiModel
import com.film.login.data.model.LoginResponse
import com.film.login_ui.LoginType
import com.film.login_ui.verifypan.LoginUiEvent.LoginSubmit
import com.film.bazar.appusercore.model.UserType
import io.reactivex.rxjava3.core.Observable

interface LoginView : BaseView {
    fun onLoginClicked(): Observable<Unit>
    fun onDoneClicked(): Observable<Int>
    fun registerLoginEvent()
    fun onSubmitClicked() : Observable<LoginSubmit>
    fun getUserName(): String
    fun getClientCode() : String
    fun getPassword(): String
    fun getUserType(): String
    fun setRetainedUser(user: String)
    fun renderLogin(uiModel: UiModel<LoginResponse>)
    fun renderProceed(uiModel: UiModel<String>)
    fun postNavigationEvent(type: LoginType)

    fun onForgotPasswordClicked(): Observable<Unit>
    fun showForgotPasswordBottomSheet()
}

data class LoginPageData(
    @JvmField val userType: UserType
)