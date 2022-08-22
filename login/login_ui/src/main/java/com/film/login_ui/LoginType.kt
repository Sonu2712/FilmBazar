package com.film.login_ui

import com.film.bazar.coreui.appcoreui.base.BaseFragment
import io.reactivex.rxjava3.subjects.PublishSubject

sealed class LoginType(val id: Int, open val data: Any? = null) {
    class Customer(override val data: Any? = null) : LoginType(0, data)
    class Guest(override val data: Any? = null) : LoginType(1)
    class Forgot(override val data: Any? = null) : LoginType(2)
    class OpenAccount(override val data: Any? = null) : LoginType(3)
}

interface LoginEventProvider {
    val navigationEvent: PublishSubject<LoginType>
}

fun BaseFragment.navigationEvent(): PublishSubject<LoginType> {
    return (this.parentFragment as? LoginEventProvider)?.navigationEvent
        ?: PublishSubject.create()
}
