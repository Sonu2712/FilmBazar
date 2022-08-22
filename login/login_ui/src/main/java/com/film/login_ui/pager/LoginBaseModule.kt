package com.film.login_ui.pager

import com.film.login_ui.base.LoginBaseFragment
import com.film.login_ui.base.LoginPagerView
import dagger.Binds
import dagger.Module

@Module
abstract class LoginBaseModule {
    @Binds
    abstract fun provideLoginPagerView(fragment: LoginBaseFragment): LoginPagerView
}
