package com.film.login_ui.openanaccount

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface OpenAnAccountView : BaseView {
    fun onPasswordChanged(): Observable<String>
    fun onNameChanged(): Observable<String>
    fun onEmailChanged(): Observable<String>
    fun onNumberChangedChanged(): Observable<String>
    fun toggleRegisterButton(isEnabled: Boolean)
    fun onRegisterClicked(): Observable<Unit>
    fun showVerifyBottomSheet()
    // fun togglePasswordStrength(password : String)
}

class OpenAnAccountPresenter @Inject constructor(
    view: OpenAnAccountView
) : BasePresenter<OpenAnAccountView>(view) {
    override fun start() {
        Observable.combineLatest(
            view.onNameChanged(),
            view.onEmailChanged(),
            view.onNumberChangedChanged(),
            view.onPasswordChanged(),
            Function4 { t1: String, t2: String, t3: String, t4: String ->
                UserRegistration(
                    t1, t2, t3, t4
                )
            }
        ).map {
            it.fName.length > 3 && it.emailId.length > 3 && it.pNumber.length > 3 && it.pass.length > 3
        }.subscribe(view::toggleRegisterButton)
            .addTo(disposable)

        view.onRegisterClicked()
            .subscribe {
                view.showVerifyBottomSheet()
            }.addTo(disposable)
    }
}

data class UserRegistration(
    val fName: String,
    val emailId: String,
    val pNumber: String,
    val pass: String
)

@Module
abstract class OpenAnAccountModule {
    @Binds
    abstract fun provideOpenAnAccountView(fragment: OpenAnAccountFragment): OpenAnAccountView
}