package com.film.bazar.home_domain

data class MovieDetail(
    val bannerInfo: MovieDetailBannerInfo,
    val movieFund: MovieFund,
    var invtInfo: List<InvestmentInfo>,
    val titleSubTitle: List<TitleSubTitle>,
    val castCrewDetail : CastCrewDetail,
    val videoInfo: List<VideoInfo>

)

data class MovieSort(
    val valueFrom : Double,
    val valueTo : Double,
    val selectedSort : MovieFilterType,
    val sort : List<MovieSortKeyValue> = emptyList()
)

data class MovieSortKeyValue(
    val label: String,
    val filterType: MovieFilterType
)

data class MovieFilter(
    val startAmount : Float,
    val endAmount : Float,
    val filterType : MovieFilterType
)

sealed class MovieFilterType{
    object RecentlyAdded : MovieFilterType()
    object PopularityHTL : MovieFilterType()
    object PopularityLTH : MovieFilterType()
    object DaysLeftForInvtHTL : MovieFilterType()
    object DaysLeftForInvtLTH : MovieFilterType()
    object Nothing : MovieFilterType()
}

data class CastCrewDetail(
    val directorName : String,
    val castCrew : List<CastCrew>
)
data class CastCrew(
    val title: String,
    val casts: List<MovieCastCrew>
)

data class MovieCastCrew(
    val imageUrl: String,
    val fName: String,
    val lName: String
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