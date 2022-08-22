package com.film.bazar.splash

import androidx.appcompat.app.AppCompatActivity
import com.film.bazar.splash.SplashActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SplashActivityModule {
    @Binds
    abstract fun provideSplashActivity(activity: SplashActivity): AppCompatActivity
}
