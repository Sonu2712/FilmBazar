package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.*
import com.film.commons.cache.InMemoryCache
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    val cache: InMemoryCache
) : HomeRepository {
    var tab: MovieTab = MovieTab.OngoingProject
    override fun getHomeData(): Observable<MovieData> {
        return Observable.just(MovieData(movieBanner, movieTab, movieInfoOnGoing))
    }

    override fun getMovieByProject(tab: MovieTab, filter : MovieFilter?): Observable<List<MovieInfo>> {
        this.tab = tab
        val list = if (tab == MovieTab.OngoingProject) movieInfoOnGoing else movieInfoPast
        val filteredList = filter?.let {
            list.filterValue(filter.startAmount.toDouble(), filter.endAmount.toDouble())
                .sortValue(it.filterType)
        } ?: list
        return Observable.just(filteredList)
    }


    override fun getMovieSort(): Observable<MovieSort> {
        val movieIfo = if (tab == MovieTab.OngoingProject) movieInfoOnGoing else movieInfoPast
        val movieFiler : MovieFilter = getCurrentFilter1()
        val valueFrom = if (movieFiler.startAmount == 0.0)
            movieIfo.minBy { data -> data.targetAmount }?.targetAmount ?: 0.0
        else movieFiler.startAmount

        val valueTo = if (movieFiler.endAmount == 0.0)
            movieIfo.maxBy { data -> data.targetAmount }?.targetAmount ?: 0.0
        else movieFiler.endAmount

        return Observable.just(
            MovieSort(
                valueFrom = movieIfo.minBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                selectedFrom = valueFrom,
                valueTo = movieIfo.maxBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                selectedTo = valueTo,
                selectedSort = movieFiler.filterType,
                sort = if (tab == MovieTab.OngoingProject) MovieSortFilter.allOngoingProjectFilterOptions else MovieSortFilter.allPastProjectFilterOptions
            )
        )
    }

    override fun saveFilter(filter: MovieFilter): Observable<MovieFilter> {
        Timber.d("FilmD Save : StartValue ${filter.startAmount}, EndAmount ${filter.endAmount}, Filter${filter.filterType.toString()}")
        return Observable.fromCallable {
            cache.set(MovieFilterCacheKey, filter)
            cache.getValue(MovieFilterCacheKey) ?: MovieFilter(
                0.0,
                0.0,
                filter.filterType
            )
        }
    }

    fun getCurrentFilter1(): MovieFilter {
        return cache.getValue(MovieFilterCacheKey)
                ?: MovieFilter(0.0, 0.0, MovieSortFilter.ADDED_NOTHING)

    }

    override fun resetFilter(): Observable<MovieSort> {
        return Observable.fromCallable {
            cache.evict(MovieFilterCacheKey)
            cache.getValue(MovieFilterCacheKey) ?: MovieSort(
                0.0,
                null,
                0.0,
                null,
                MovieSortFilter.ADDED_NOTHING
            )
        }
    }

    override fun getMovieDetail(movieId: Int, tab: String): Observable<MovieDetail> {
        return Observable.just(movieDetail(tab))
    }

    companion object {
        const val MovieFilterCacheKey = "moviefiltercachekey"
    }
}