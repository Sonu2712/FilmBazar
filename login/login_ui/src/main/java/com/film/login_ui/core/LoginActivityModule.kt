package com.film.login_ui.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.film.bazar.coreui.appcoreui.base.ActivityDelegate
import com.film.bazar.coreui.navigatorlib.*
import com.film.login_ui.BaseLoginFragmentProvider
import com.film.login_ui.LoginFragmentResolver
import dagger.Binds
import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module(includes = [BaseLoginFragmentProvider::class])
abstract class LoginActivityModule {
    @Binds
    abstract fun provideNavigator(activity: LoginActivity): Navigator

   @Binds
   abstract fun provideActivityDelegate(activity: LoginActivity): ActivityDelegate

    @Binds
    abstract fun provideFragmentTransitionHelper(transitionHelper: FragmentTransitionHelperImpl): FragmentTransitionHelper

    @Binds
    abstract fun provideFragmentActivity(activity: LoginActivity): FragmentActivity

    @Binds
    abstract fun provideAppCompatActivity(activity: LoginActivity): AppCompatActivity

    @Binds
    abstract fun provideLoginNavigator(loginNavigator: LoginNavigatorImpl): LoginNavigator

    companion object {
        @Provides
        fun provideFragmentManager(activity: LoginActivity): FragmentManager {
            return activity.supportFragmentManager
        }

        @Provides
        fun provideNavigationCallback(): NavigationCallback {
            return object : NavigationCallback {
                override fun onPageOpened(
                    fragment: Fragment,
                    addedToStack: Boolean
                ) {
                    Timber.d("Page Opened %s, On Stack : %s", fragment, addedToStack)
                }

                override fun onBackNavigation() {
                    Timber.d("onBackNavigation called")
                }
            }
        }

        @Provides
        fun provideFragmentResolver(): FragmentResolver {
            return LoginFragmentResolver
        }
    }
}
