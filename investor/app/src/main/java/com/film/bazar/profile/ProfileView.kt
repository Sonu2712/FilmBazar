package com.film.bazar.profile

import com.film.app.core.base.BaseView
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface ProfileView : BaseView {
    fun onLogoutClicked() : Observable<Unit>
    fun showLogoutConfirmationDialog()
    fun onLogoutConfirmed() : Observable<ProfileEvent.LogoutConfirmed>
    fun renderSuccessLogout(uiModel: UiModel<Boolean>)
}