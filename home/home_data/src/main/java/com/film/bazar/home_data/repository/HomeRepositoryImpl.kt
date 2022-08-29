package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.*
import com.film.commons.cache.InMemoryCache
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    val cache: InMemoryCache
) : HomeRepository {
    var tab: MovieTab = MovieTab.OngoingProject
    override fun getHomeData(): Observable<MovieData> {
        return Observable.just(MovieData(movieBanner, movieTab, movieInfoOnGoing))
    }

    override fun getMovieByProject(tab: MovieTab, sort: MovieSort?): Observable<List<MovieInfo>> {
        this.tab = tab
        val list = if (tab == MovieTab.OngoingProject) movieInfoOnGoing else movieInfoPast
        sort?.let {
            list.filterValue(sort.valueFrom, sort.valueTo)
                .sortValue(it.selectedSort)
        }
        return Observable.just(list)
    }


    override fun getMovieSort(): Observable<MovieSort> {
        val movieIfo = if (tab == MovieTab.OngoingProject) movieInfoOnGoing else movieInfoPast
        return Observable.just(
            MovieSort(
                valueFrom = movieIfo.minBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                valueTo = movieIfo.maxBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                selectedSort = MovieFilterType.Nothing,
                sort = listOf(
                    MovieSortKeyValue(
                        label = "Recently added",
                        filterType = MovieFilterType.RecentlyAdded
                    ),
                    MovieSortKeyValue(
                        label = "Popularity  -  high to low",
                        filterType = MovieFilterType.PopularityHTL
                    ),
                    MovieSortKeyValue(
                        label = "Popularity  -  low to high",
                        filterType = MovieFilterType.PopularityLTH
                    ),
                    MovieSortKeyValue(
                        label = "Day’s left for investment   -  high to low",
                        filterType = MovieFilterType.DaysLeftForInvtHTL
                    ),
                    MovieSortKeyValue(
                        label = "Day’s left for investment   -  low to high",
                        filterType = MovieFilterType.DaysLeftForInvtLTH
                    )
                )
            )
        )
    }

    override fun saveFilter(filter: MovieSort): Observable<MovieSort> {
        return Observable.fromCallable {
            cache.set(MovieFilterCacheKey, filter)
            cache.getValue(MovieFilterCacheKey) ?: MovieSort(
                0.0,
                0.0,
                MovieFilterType.Nothing
            )
        }
    }

    override fun getCurrentFilter(): Observable<MovieSort> {
        return Observable.just(
            cache.getValue(MovieFilterCacheKey)
                ?: MovieSort(0.0, 0.0, MovieFilterType.Nothing)
        )
    }

    override fun resetFilter(): Observable<MovieSort> {
        return Observable.fromCallable {
            cache.evict(MovieFilterCacheKey)
            cache.getValue(MovieFilterCacheKey) ?: MovieSort(
                0.0,
                0.0,
                MovieFilterType.Nothing
            )
        }
    }

    fun List<MovieInfo>.filterValue(fromValue: Double, toValue: Double): List<MovieInfo> {
        return filter {
            it.targetAmount in fromValue..toValue
        }
    }

    fun List<MovieInfo>.sortValue(sort: MovieFilterType): List<MovieInfo> {
        return when (sort) {
            MovieFilterType.RecentlyAdded -> sortedBy { it.noOfDaysLeft }
            MovieFilterType.PopularityHTL -> sortedByDescending { it.noOfPeopleInvt }
            MovieFilterType.PopularityHTL -> sortedBy { it.noOfPeopleInvt }
            MovieFilterType.DaysLeftForInvtHTL -> sortedByDescending { it.noOfDaysLeft }
            MovieFilterType.PopularityLTH -> sortedBy { it.noOfPeopleInvt }
            else -> this
        }
    }

    override fun getMovieDetail(movieId: Int, tab: String): Observable<MovieDetail> {
        return Observable.just(movieDetail(tab))
    }

    companion object {
        const val MovieFilterCacheKey = "moviefiltercachekey"
    }
}