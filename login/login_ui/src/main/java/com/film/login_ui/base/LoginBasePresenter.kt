package com.film.login_ui.base

import com.film.app.core.base.BasePresenter
import com.film.commons.rx.addTo
import com.film.login_ui.LoginType
import com.film.login_ui.core.LoginNavigator
import javax.inject.Inject

class LoginBasePresenter @Inject constructor(
    view: LoginPagerView,
    val navigator: LoginNavigator
) : BasePresenter<LoginPagerView>(view) {

    override fun start() {
        view.onRegisterClicked()
            .map { LoginType.OpenAccount() }
            .subscribe(view::onActionSelected)
            .addTo(disposable)


    }
}