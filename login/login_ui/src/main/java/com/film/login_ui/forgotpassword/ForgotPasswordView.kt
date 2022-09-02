package com.film.login_ui.forgotpassword

import com.film.app.core.base.BaseView
import com.film.bazar.coreui.views.AutoReadOTPEvents
import io.reactivex.rxjava3.core.Observable

interface ForgotPasswordView : BaseView {
    fun onCloseClicked() : Observable<Unit>
    fun dismissBottomSheet()

    fun onDoneClicked() : Observable<Unit>
    fun onEmailIdChanged() : Observable<Int>
    fun toggleDoneButton(isEnabled : Boolean)
    fun renderDone(isVerifyOtp : Boolean)

    fun onVerifyClicked() : Observable<Unit>
    fun onOtpChanged() : Observable<AutoReadOTPEvents.ToggleSubmitButtonEvent>
    fun toggleVerifyButton(isEnabled: Boolean)
    fun renderVerify(isFromReset : Boolean)

    fun onResendOtpClicked(): Observable<AutoReadOTPEvents.ResendOTPEvent>
    fun getEmailId() : String
    fun renderResendOtp()

    fun onResetDoneClicked() : Observable<Unit>
    fun onPasswordChanged() : Observable<String>
    fun onConfirmPasswordChanged() : Observable<String>
    fun toggleRestButton(isEnabled: Boolean)
    fun renderResetDone()

    fun setupView(isFromForgotPassword : Boolean)
}