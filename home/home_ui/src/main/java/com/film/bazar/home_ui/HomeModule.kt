package com.film.bazar.home_ui

import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {
    @Binds
    abstract fun provideHomeView(fragment: HomeFragment): HomeView

    @Binds
    abstract fun provideHomeNavigator(navigatorImpl: HomeNavigatorImpl): HomeNavigator
}
