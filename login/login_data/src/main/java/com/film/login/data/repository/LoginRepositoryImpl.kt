package com.film.login.data.repository

import android.content.Context
import com.film.annotations.AppMenu
import com.film.app.core.prefs.StringPreference
import com.film.bazar.appuser.UserChangeEvent
import com.film.bazar.appuser.UserEvent
import com.film.bazar.appuser.helper.LoginPrefStore
import com.film.bazar.appuser.model.LoginInfo
import com.film.bazar.appuser.repository.UserManagerImpl
import com.film.commons.rx.addTo
import com.film.commons.utils.Clock
import com.film.login.data.model.LoginFormOutput
import com.film.login.data.model.LoginResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject internal constructor(
    internal val userRepository: AppUserRepository,
    internal val userManager: UserManagerImpl,
    /*internal val tokenManager: TokenManager,*/
    val clock: Clock,
    internal val prefStore: LoginPrefStore,
    internal val userChangeEvent: UserChangeEvent,
    @AppMenu val appMenuPref: StringPreference,
    val context: Context
) : LoginRepository {
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun login(
        loginFormOutput: LoginFormOutput
    ): Observable<LoginResponse> {
        return userRepository.login(loginFormOutput).doOnNext { response ->
            appMenuPref.delete()
            if (!response.forceChange && !response.passwordExpired) {
                saveLoginResponse(response).also {
                    userManager.saveCredentials(
                        LoginInfo(
                            userCode = response.user!!.userCode,
                            password = loginFormOutput.password,
                            mobileNo = response.user.mobileNumber!!,
                            userType = response.user.userType
                        )
                    )
                }
            }
        }
    }

    internal fun saveLoginResponse(response: LoginResponse) {
        response.tokenInfo?.let {
            /*it.let {
                tokenManager.saveTokens(it)
            }*/
        }
        //tokenManager.saveLoginTime(response.loginTime)
        response.user?.let {
            userManager.saveUser(it)
        }
    }

    override fun logoutUser(): Completable {
        return Completable.fromAction {
            if (userManager.isLoggedIn()) {
               // tokenManager.clear()
                userManager.logout()
            }
        }.subscribeOn(Schedulers.io())
    }

    fun start() {
        userManager.onUserChanged().subscribe {
            when (it) {
                is UserEvent.LoggedIn -> {
                    //tokenManager.start()
                    userChangeEvent.onLogin(it.user)
                }
                UserEvent.LoggedOut -> {
                    userChangeEvent.onLogout()
                }
                UserEvent.OpenUser -> {
                }
            }
        }.addTo(disposable)
    }

    fun stop() {
        disposable.clear()
        //tokenManager.stop()
    }
}