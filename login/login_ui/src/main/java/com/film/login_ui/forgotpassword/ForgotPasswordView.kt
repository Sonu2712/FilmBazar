package com.film.login_ui.forgotpassword

import com.film.app.core.base.BaseView
import com.film.bazar.coreui.views.AutoReadOTPEvents
import io.reactivex.rxjava3.core.Observable

interface ForgotPasswordView : BaseView {
    fun onCloseClicked() : Observable<Unit>
    fun dismissBottomSheet()

    fun onDoneClicked() : Observable<Unit>
    fun renderDone(isVerifyOtp : Boolean)

    fun onVerifyClicked() : Observable<Unit>
    fun renderVerify(isFromReset : Boolean)

    fun onResetDoneClicked() : Observable<Unit>
    fun renderResetDone()
    fun onResendOtpClicked(): Observable<AutoReadOTPEvents.ResendOTPEvent>
    fun getEmailId() : String
    fun renderResendOtp()

    fun setupView(isFromForgotPassword : Boolean)
}