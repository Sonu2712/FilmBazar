package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.*
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
) : HomeRepository {
    override fun getHomeData(): Observable<MovieData> {
        return Observable.just(MovieData(movieBanner, MovieModel(movieTab,movieInfo)))
    }

    override fun getMovieDetail(): Observable<MovieDetail> {
        return Observable.just(movieDetail)
    }

    override fun getCastCrew(id: Int): Observable<CastCrewDetail> {
        return Observable.just(CastCrewDetail(directorName = "ABC", castCrew = listOf(topCast(), topCrew())))
    }
}