package com.film.bazar

import com.film.app.core.base.ViewContainer
import dagger.Module
import dagger.Provides

@Module
object UiModule {
    @Provides
    internal fun provideViewContainer(): ViewContainer {
        return ViewContainer.DEFAULT
    }
}