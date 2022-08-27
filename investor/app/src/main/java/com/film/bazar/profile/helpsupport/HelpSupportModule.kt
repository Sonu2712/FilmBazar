package com.film.bazar.profile.helpsupport

import dagger.Binds
import dagger.Module

@Module
abstract class HelpSupportModule {
    @Binds
    abstract fun provideHelpSupportView(fragment: HelpSupportFragment) : HelpSupportView
}