package com.film.bazar.home_data.repository

import android.content.SharedPreferences
import com.film.app.core.prefs.StringPreference
import com.film.bazar.home_domain.*
import com.film.commons.cache.InMemoryCache
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    val cache: InMemoryCache,
    val preferences: SharedPreferences,
    private val gson: Gson
) : HomeRepository {
    fun sortPref(tabLabel: String): StringPreference {
        return StringPreference(preferences, "MOVIE_SORT_PREF$tabLabel", "")
    }

    override fun getHomeData(): Observable<MovieData> {
        val tab = getCurrentTab1() ?: MovieTab.ONGOING_PROJECT
        val list = if (tab.isOnGoingProject()) movieInfoOnGoing else movieInfoPast
        val movieFiler: MovieFilter? = getCurrentFilter1(tab)
        val filtered = list.sortingFilter(movieFiler)
        return Observable.just(MovieData(movieBanner, MovieTab.allProjectTab, filtered))
    }

    override fun getMovieByProject(
        tab: MovieTab,
        filter: MovieFilter?
    ): Observable<List<MovieInfo>> {
        val currentTab = getCurrentTab1() ?: MovieTab.ONGOING_PROJECT
        val movieIfo = if (currentTab.isOnGoingProject()) movieInfoOnGoing else movieInfoPast
        val movieFiler: MovieFilter? = getCurrentFilter1(currentTab)
        return Observable.just(movieIfo.sortingFilter(movieFiler))
    }

    override fun getMovieSort(): Observable<MovieSort> {
        val currentTab = getCurrentTab1() ?: MovieTab.ONGOING_PROJECT
        val movieIfo = if (currentTab.isOnGoingProject()) movieInfoOnGoing else movieInfoPast
        val movieFiler: MovieFilter? = getCurrentFilter1(currentTab)
        val valueFrom =
            movieFiler?.startAmount ?: (movieIfo.minBy { data -> data.targetAmount }?.targetAmount
                ?: 0.0)
        val valueTo =
            movieFiler?.endAmount ?: (movieIfo.maxBy { data -> data.targetAmount }?.targetAmount
                ?: 0.0)
        val selectedSort = movieFiler?.filterType ?: MovieSortFilter.ADDED_NOTHING

        return Observable.just(
            MovieSort(
                valueFrom = movieIfo.minBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                selectedFrom = valueFrom,
                valueTo = movieIfo.maxBy { data -> data.targetAmount }?.targetAmount ?: 0.0,
                selectedTo = valueTo,
                selectedSort = selectedSort,
                sort = if (currentTab.isOnGoingProject()) MovieSortFilter.allOngoingProjectFilterOptions else MovieSortFilter.allPastProjectFilterOptions
            )
        )
    }

    override fun saveFilter(filter: MovieFilter): Observable<MovieFilter> {
        val currentTab = getCurrentTab1() ?: MovieTab.ONGOING_PROJECT
        sortPref(currentTab.label).set(gson.toJson(filter))
        return Observable.just(filter)
    }

    override fun saveMovieTab(movieTab: MovieTab) {
        cache.set(MovieTabCacheKey, movieTab)
        cache.getValue(MovieTabCacheKey) ?: MovieTab.ONGOING_PROJECT
    }

    fun getCurrentTab1(): MovieTab? {
        return cache.getValue(MovieTabCacheKey)
    }

    fun getCurrentFilter1(currentTab: MovieTab): MovieFilter? {
        return try {
            gson.fromJson(sortPref(currentTab.label).get(), MovieFilter::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun resetFilter(): Observable<MovieFilter> {
        val currentTab = getCurrentTab1() ?: MovieTab.ONGOING_PROJECT
        sortPref(currentTab.label).delete()
        return Observable.just(resetSortFilter(currentTab))
    }

    override fun getMovieDetail(movieId: Int, tab: String): Observable<MovieDetail> {
        return Observable.just(movieDetail(tab))
    }

    companion object {
        const val MovieTabCacheKey = "movietabcachekey"
    }
}

fun resetSortFilter(currentTab: MovieTab) : MovieFilter {
    return MovieFilter(
        tab = currentTab,
        startAmount = 0.0,
        endAmount = 0.0,
        filterType = MovieSortFilter.ADDED_NOTHING
    )
}

fun List<MovieInfo>.sortingFilter(filter: MovieFilter?): List<MovieInfo> {
    return filter?.let {
        this.filterValue(filter.startAmount, filter.endAmount)
            .sortValue(filter.filterType)
    } ?: this
}

val movieInfoOnGoing = listOf(
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 500.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 3,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 5,
        targetAmount = 1000.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 1,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 1500.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 8,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 15,
        targetAmount = 2000.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 9,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 2500.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 10,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 50,
        targetAmount = 3000.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 11,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 90,
        targetAmount = 3500.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.ONGOING_PROJECT
    )
)
val movieInfoPast = listOf(
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 12,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 4000.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 13,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 5,
        targetAmount = 4500.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 14,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 5000.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 57,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 15,
        targetAmount = 5500.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 55,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 6000.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 110,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 50,
        targetAmount = 6500.0,
        targetGoal = 100000000.0,
        orderAction = "Withdraw",
        tab = MovieTab.PAST_PROJECT
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 15,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 90,
        targetAmount = 7000.0,
        targetGoal = 100000000.0,
        orderAction = "Buy",
        tab = MovieTab.PAST_PROJECT
    )
)