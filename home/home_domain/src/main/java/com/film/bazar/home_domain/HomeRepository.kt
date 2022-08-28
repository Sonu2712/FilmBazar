package com.film.bazar.home_domain

import io.reactivex.rxjava3.core.Observable

interface HomeRepository {
    fun getHomeData() : Observable<MovieData>
    fun getMovieByProject(tab: MovieTab) : Observable<List<MovieInfo>>

    fun getMovieDetail() : Observable<MovieDetail>

    fun getCastCrew(id : Int) : Observable<CastCrewDetail>
}