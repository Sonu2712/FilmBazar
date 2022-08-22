package com.film.bazar.home_domain

import io.reactivex.rxjava3.core.Observable

interface HomeRepository {
    fun getHomeData() : Observable<MovieData>
}