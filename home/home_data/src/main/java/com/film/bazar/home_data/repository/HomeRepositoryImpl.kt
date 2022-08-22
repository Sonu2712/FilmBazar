package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.HomeRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
) : HomeRepository {
    override fun welcomeHome(): Observable<String> {
        return Observable.just("Welcome Home")
    }
}