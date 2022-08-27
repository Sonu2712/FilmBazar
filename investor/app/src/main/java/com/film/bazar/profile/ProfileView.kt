package com.film.bazar.profile

import com.film.app.core.base.BaseView
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface ProfileView : BaseView {
    fun onPaymentDetailsClicked() : Observable<Unit>

    fun onNotificationClicked() : Observable<Boolean>
    fun renderNotification(uiModel : UiModel<String>)

    fun onEditProfileClicked() : Observable<Unit>
    fun onHelpSupportClicked() : Observable<Unit>
    fun onTermsConditionClicked() : Observable<Unit>

    fun onLogoutClicked() : Observable<Unit>
    fun showLogoutConfirmationDialog()
    fun onLogoutConfirmed() : Observable<ProfileUiEvent.LogoutConfirmed>
    fun renderSuccessLogout(uiModel: UiModel<Boolean>)
}