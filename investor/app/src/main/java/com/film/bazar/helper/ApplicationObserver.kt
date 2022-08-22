package com.film.bazar.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.film.login.data.repository.LoginRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationObserver @Inject constructor(
    val loginRepository: LoginRepositoryImpl
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        appInForeground = true
        loginRepository.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        appInForeground = false
        loginRepository.stop()
    }

    companion object {
        var appInForeground: Boolean = false
    }
}
