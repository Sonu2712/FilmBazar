package com.film.bazar

import com.film.annotations.ActivityScoped
import com.film.login.data.provider.LoginDataModule
import com.film.login_ui.core.LoginActivity
import com.film.login_ui.core.LoginActivityModule
import com.film.bazar.splash.SplashActivity
import com.film.bazar.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [LoginDataModule::class])
abstract class CommonActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    internal abstract fun bindLoginActivity(): LoginActivity
}