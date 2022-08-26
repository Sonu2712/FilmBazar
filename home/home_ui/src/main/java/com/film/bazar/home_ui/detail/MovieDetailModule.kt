package com.film.bazar.home_ui.detail

import com.film.bazar.home_ui.HomeNavigator
import com.film.bazar.home_ui.HomeNavigatorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailModule {
    @Binds
    abstract fun provideMovieDetailView(fragment: MovieDetailFragment) : MovieDetailView

    @Binds
    abstract fun provideHomeNavigator(navigatorImpl: HomeNavigatorImpl): HomeNavigator
}