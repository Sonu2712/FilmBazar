package com.film.bazar.home_domain

import io.reactivex.rxjava3.core.Observable

interface HomeRepository {
    fun getHomeData(): Observable<MovieData>
    fun getMovieSort(): Observable<MovieSort>
    fun getCurrentFilter(): Observable<MovieSort>
    fun resetFilter(): Observable<MovieSort>
    fun saveFilter(filter: MovieSort): Observable<MovieSort>
    fun getMovieByProject(tab: MovieTab, sort: MovieSort? = null): Observable<List<MovieInfo>>

    fun getMovieDetail(movieId: Int, movieTab: String): Observable<MovieDetail>
}