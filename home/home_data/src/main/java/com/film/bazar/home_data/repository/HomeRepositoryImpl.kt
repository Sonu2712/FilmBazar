package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.HomeRepository
import com.film.bazar.home_domain.MovieData
import com.film.bazar.home_domain.MovieInfo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
) : HomeRepository {
    override fun getHomeData(): Observable<MovieData> {
        return Observable.just(MovieData(movieBanner, movieTab,movieInfo))
    }
}