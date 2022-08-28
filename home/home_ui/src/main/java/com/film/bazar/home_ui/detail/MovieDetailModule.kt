package com.film.bazar.home_ui.detail

import com.film.bazar.home_ui.HomeNavigator
import com.film.bazar.home_ui.HomeNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MovieDetailModule {
    @Binds
    abstract fun provideMovieDetailView(fragment: MovieDetailFragment) : MovieDetailView

    @Binds
    abstract fun provideHomeNavigator(navigatorImpl: HomeNavigatorImpl): HomeNavigator

    companion object{
        @Provides
        fun provideMovieId(fragment: MovieDetailFragment) : Int{
            return fragment.arguments?.getInt(MovieDetailView.ARG_MOVIE_ID) ?: -1
        }

        @Provides
        fun provideMovieTabType(fragment: MovieDetailFragment) : String{
            return fragment.arguments?.getString(MovieDetailView.ARG_MOVIE_TYPE) ?: ""
        }
    }
}