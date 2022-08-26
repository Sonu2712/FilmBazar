package com.film.bazar.profile

import dagger.Binds
import dagger.Module

@Module
abstract class ProfileModule {
    @Binds
    abstract fun provideProfileView(fragment: ProfileFragment) : ProfileView
}