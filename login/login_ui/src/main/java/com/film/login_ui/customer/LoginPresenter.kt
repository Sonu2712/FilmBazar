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
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    view: LoginView,
    val userManager: UserManager,
    val loginRepository: LoginRepository,
    val navigator: LoginNavigator,
    val appInfo: AppInfo,
    var preferences: SharedPreferences,
    @param:AppShortCutId protected val appShortCutId: StringPreference
) : BasePresenter<LoginView>(view) {

    var resendCounter = 4
    override fun start() {
        val loginInfo = userManager.getCredentials()
        if (loginInfo != null && userManager.getUserType().isCustomer) {
            view.setRetainedUser(loginInfo.userCode)
        }

        Observable.merge(view.onLoginClicked(), view.onDoneClicked())
            .subscribe { view.registerLoginEvent() }
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

        view.onForgotPasswordClicked()
            .map { LoginType.Forgot() }
            .subscribe(view::postNavigationEvent)
            .addTo(disposable)

        view.onSignUpClicked()
            .map { LoginType.Guest() }
            .subscribe(view::postNavigationEvent)
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