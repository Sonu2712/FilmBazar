package com.film.bazar.core

import com.film.bazar.menu.AppFragmentResolver
import com.film.bazar.coreui.navigatorlib.FragmentResolver
import dagger.Module
import dagger.Provides

@Module
object MainActivityModule {
    @Provides
    fun provideFragmentResolver(): FragmentResolver {
        return AppFragmentResolver
    }
}
