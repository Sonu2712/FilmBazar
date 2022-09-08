package com.film.login_ui.verification

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.views.AutoReadOTPEvents
import com.film.commons.rx.addTo
import com.film.login_ui.core.LoginNavigator
import com.film.login_ui.nav.LoginConstants
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface VerificationBottomSheetView : BaseView {
    fun onCloseClicked(): Observable<Unit>
    fun dismissBottomSheet()

    fun onVerifyClicked(): Observable<Unit>
    fun onOtpChanged(): Observable<AutoReadOTPEvents.ToggleSubmitButtonEvent>
    fun toggleVerifyButton(isEnabled: Boolean)
    fun renderVerify(isFromReset: Boolean)

    fun onResendOtpClicked(): Observable<AutoReadOTPEvents.ResendOTPEvent>
    fun getEmailId(): String
    fun renderResendOtp()
}

class VerificationBottomSheetPresenter @Inject constructor(
    view: VerificationBottomSheetView,
    val navigator: LoginNavigator
) : BasePresenter<VerificationBottomSheetView>(view) {
    override fun start() {

        view.onCloseClicked()
            .subscribe {
                view.dismissBottomSheet()
            }.addTo(disposable)

        view.onOtpChanged()
            .subscribe {
                view.toggleVerifyButton(it.toggle)
            }.addTo(disposable)

        view.onVerifyClicked()
            .subscribe {
                navigator.goBack()
                navigator.openPage(LoginConstants.NAVIGATE_TO_LOGIN_BASE, false)
                view.dismissBottomSheet()
            }.addTo(disposable)

        view.onResendOtpClicked()
            .map { view.getEmailId() }
            .subscribe {
                view.renderResendOtp()
            }.addTo(disposable)

    }
}

@Module
abstract class VerificationBottomSheetModule {
    @Binds
    abstract fun provideVerificationBottomSheetView(fragment: VerificationBottomSheetFragment): VerificationBottomSheetView
}