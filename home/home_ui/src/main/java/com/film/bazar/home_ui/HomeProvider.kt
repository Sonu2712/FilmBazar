package com.film.bazar.home_ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeProvider {
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun provideHomeFragmentFactory(): HomeFragment
}
