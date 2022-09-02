package com.film.login_ui.customer

import android.content.SharedPreferences
import com.film.app.core.base.BasePresenter
import com.film.app.core.prefs.StringPreference
import com.film.commons.appinfo.AppInfo
import com.film.commons.data.onSuccess
import com.film.commons.rx.addTo
import com.film.login.data.repository.LoginRepository
import com.film.login_ui.LoginType
import com.film.login_ui.core.LoginNavigator
import com.film.annotations.AppShortCutId
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.appusercore.model.UserType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    view: LoginView,
    val userManager: UserManager,
    val loginRepository: LoginRepository,
    val navigator: LoginNavigator,
    var preferences: SharedPreferences,
    @param:AppShortCutId protected val appShortCutId: StringPreference
) : BasePresenter<LoginView>(view) {

    var resendCounter = 4
    override fun start() {
        val loginInfo = userManager.getCredentials()
        if (loginInfo != null && userManager.getUserType().isCustomer) {
            view.setRetainedUser(loginInfo.userCode)
        }

        val emailIdChangedObs = view.onEmailIdChanged().share()

        Observable.combineLatest(emailIdChangedObs.filter { view.isLoginWithIdPassword() },
            view.onPasswordChanged(), BiFunction { t1 : String, t2 : String -> t1 to t2 })
            .map { it.first.length > 5 && it.second.length > 5 }
            .subscribe(view::toggleLoginButton)
            .addTo(disposable)

        view.onResendOtpClicked()
            .map { view.getEmailId() }
            .subscribe {
                view.renderResendOtp()
            }.addTo(disposable)

        view.onOtpChanged()
            .subscribe {
                view.toggleLoginButton(it.toggle)
            }.addTo(disposable)

        emailIdChangedObs
            .map { !view.isLoginWithIdPassword() && it.length > 5 }
            .subscribe(view::toggleLoginButton)
            .addTo(disposable)

        Observable.merge(view.onLoginClicked(), view.onDoneClicked())
            .subscribe { view.registerLoginEvent() }
            .addTo(disposable)

        view.onRequestOtpClicked()
            .subscribe { view.toggleViewForResend(true) }
            .addTo(disposable)

        view.onSubmitClicked()
            .map { view.getLoginFormInput() }
            .switchMap { input ->
                LoginValidator.validate(input)
                    .flatMap(loginRepository::login)
                    .compose(applyUiModel())
            }.subscribe { it ->
                view.renderLogin(it)
                it.onSuccess {
                    navigator.openHome()
                }
            }.addTo(disposable)

        view.onVerifyOtpClicked()
            .map { view.getOtp() }
            .filter { it.length > 4}
            .map { view.getLoginFormInput().copy(
                userType = UserType.from(UserType.TRADING.label),
                userName = "ekjfsevkbsvsb",
                clientCode = "dvdsvsvs",
                password = "svddsvsv"
            ) }
            .switchMap { input ->
                LoginValidator.validate(input)
                    .flatMap(loginRepository::login)
                    .compose(applyUiModel())
            }.subscribe { it ->
                view.renderLogin(it)
                it.onSuccess {
                    navigator.openHome()
                }
            }.addTo(disposable)

        view.onForgotPasswordClicked()
            .subscribe { view.showForgotPasswordBottomSheet() }
            .addTo(disposable)
    }

    private fun LoginView.getLoginFormInput(): LoginFormInput {
        return LoginFormInput(
            userType = UserType.from(getUserType()),
            userName = getUserName(),
            clientCode = getClientCode(),
            password = getPassword()
        )
    }
}