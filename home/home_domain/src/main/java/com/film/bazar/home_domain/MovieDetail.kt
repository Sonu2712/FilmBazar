package com.film.bazar.home_domain

data class MovieDetail(
    val bannerInfo: MovieDetailBannerInfo,
    val movieFund: MovieFund,
    var invtInfo: List<InvestmentInfo>,
    val titleSubTitle: List<TitleSubTitle>,
    val castCrewDetail: CastCrewDetail,
    val videoInfo: List<VideoInfo>

)

data class MovieSort(
    val valueFrom: Double,
    val selectedFrom: Double?,
    val valueTo: Double,
    val selectedTo: Double? = null,
    val selectedSort: MovieSortFilter,
    val sort: List<MovieSortFilter> = emptyList()
)

data class MovieFilter(
    val startAmount: Double,
    val endAmount: Double,
    val filterType: MovieSortFilter
)

data class CastCrewDetail(
    val directorName: String,
    val casts: CastCrew,
    val crews: CastCrew
)

data class CastCrew(
    val title: String,
    val casts: List<MovieCastCrew>
)

data class MovieCastCrew(
    val imageUrl: String,
    val fName: String,
    val lName: String,
    val position: String
)

data class TitleSubTitle(
    val invtMsg1: String,
    val invtMsg2: String
)

data class MovieDetailBannerInfo(
    val bannerUrl: String,
    val title: String,
    val movieGenre: List<String>
)

data class MovieFund(
    val daysLeft: Int,
    val fundingPer: Int,
    val targetAmount: Double,
    val targetGoalAmount: Double
)

data class InvestmentInfo(
    val imageUrl: String,
    val numberValue: Double,
    val label: String,
    val subLabel: String
)

data class VideoInfo(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val videoId: String,
    val youtubeUrl: String
)