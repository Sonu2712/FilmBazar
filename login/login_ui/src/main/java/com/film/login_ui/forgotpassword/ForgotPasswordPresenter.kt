package com.film.login_ui.forgotpassword

import com.film.app.core.base.BasePresenter
import com.film.commons.rx.addTo
import javax.inject.Inject

class ForgotPasswordPresenter @Inject constructor(
    view: ForgotPasswordView
) : BasePresenter<ForgotPasswordView>(view) {

    override fun start() {
        view.setupView(true)
        view.onCloseClicked()
            .subscribe {
                view.dismissBottomSheet()
            }.addTo(disposable)

        view.onDoneClicked()
            .subscribe {
                view.renderDone(true)
            }.addTo(disposable)

        view.onResendOtpClicked()
            .map { view.getEmailId() }
            .subscribe {
                view.renderResendOtp()
            }.addTo(disposable)

        view.onVerifyClicked()
            .subscribe {
                view.renderVerify(true)
            }.addTo(disposable)

        view.onResetDoneClicked()
            .subscribe{
                view.renderResetDone()
            }
            .addTo(disposable)

    }
}