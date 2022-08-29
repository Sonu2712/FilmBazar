package com.film.bazar.home_domain

import io.reactivex.rxjava3.core.Observable

interface HomeRepository {
    fun getHomeData(): Observable<MovieData>
    fun getMovieByProject(tab: MovieTab, filter: MovieFilter? = null): Observable<List<MovieInfo>>

    fun getMovieSort(): Observable<MovieSort>
    fun resetFilter(): Observable<MovieSort>
    fun saveFilter(filter: MovieFilter): Observable<MovieFilter>

    fun getMovieDetail(movieId: Int, movieTab: String): Observable<MovieDetail>
}