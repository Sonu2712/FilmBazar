package com.film.login_ui.forgotpassword

import com.film.app.core.base.BasePresenter
import com.film.commons.rx.addTo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
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

        view.onEmailIdChanged()
            .map { it > 5 }
            .subscribe {
                view.toggleDoneButton(it)
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

        view.onOtpChanged()
            .subscribe {
                view.toggleVerifyButton(it.toggle)
            }.addTo(disposable)

        Observable.combineLatest(view.onPasswordChanged(), view.onConfirmPasswordChanged(),
            BiFunction { t1 : String, t2 : String -> t1 to t2
        }).map {
           it.first.length >5 && it.second.length > 5
        }.subscribe {
            view.toggleRestButton(it)
        }.addTo(disposable)

        view.onResetDoneClicked()
            .subscribe{
                view.renderResetDone()
            }
            .addTo(disposable)

    }
}