package com.film.bazar.home_domain

data class MovieData(
    val banner: List<MovieBanner>,
    val tab: List<MovieTab>,
    val info: List<MovieInfo>
)

data class MovieInfo(
    val imgUrl: String,
    val title: String,
    val directorName: String,
    val noOfDaysLeft: Int,
    val noOfPeopleInvt: Double,
    val perFoundProgress: Int,
    val targetAmount: Double,
    val targetGoal: Double,
    val orderAction: String,
    val tab: MovieTab
)

data class MovieBanner(
    val imageUrl: String,
    val deeplink: String
)