package com.film.bazar

import com.film.bazar.nav.DebugFragmentResolver
import com.film.bazar.coreui.navigatorlib.FragmentResolver
import dagger.Module
import dagger.Provides

@Module
object DebugActivityModule {
    @Provides
    fun provideFragmentResolver(): FragmentResolver {
        return DebugFragmentResolver
    }
}
