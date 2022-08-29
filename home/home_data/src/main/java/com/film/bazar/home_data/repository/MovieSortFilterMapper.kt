package com.film.bazar.home_data.repository

import com.film.bazar.home_domain.MovieFilter
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_domain.MovieSortFilter

fun List<MovieInfo>.filterValue(fromValue: Double, toValue: Double): List<MovieInfo> {
    return filter {
        it.targetAmount in fromValue..toValue
    }
}

fun List<MovieInfo>.sortValue(sort: MovieSortFilter): List<MovieInfo> {
    return when {
        sort.isRecentlyAdded() -> sortedBy { it.noOfDaysLeft }
        sort.isPopularityHighToLow() -> sortedByDescending { it.perFoundProgress }
        sort.isPopularityLowToHigh() -> sortedBy { it.perFoundProgress }
        sort.isDaysLeftHighToLow() -> sortedByDescending { it.noOfDaysLeft }
        sort.isDaysLeftLowToHigh() -> sortedBy { it.noOfPeopleInvt }
        else -> this
    }
}