package com.film.bazar.home_data.repository

import android.net.Uri
import com.film.bazar.home_domain.*

val movieBanner = listOf(
    MovieBanner(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        deeplink = ""
    ),
    MovieBanner(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        deeplink = ""
    ),
    MovieBanner(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        deeplink = ""
    )
)

val movieTab = listOf(MovieTab.OngoingProject, MovieTab.PastProjects)

val movieInfo = listOf(
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 5,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 15,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 75,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 50,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    ),
    MovieInfo(
        imgUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        directorName = "Sam Raimi",
        noOfDaysLeft = 5,
        noOfPeopleInvt = 100000.0,
        perFoundProgress = 90,
        targetAmount = 50875.0,
        targetGoal = 100000000.0,
        orderAction = "Buy"
    )
)
val movieDetail = MovieDetail(
    bannerInfo = MovieDetailBannerInfo(
        bannerUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        movieGenre = listOf("Action", "Adventure", "Fantasy")
    ),
    movieFund = MovieFund(
        daysLeft = 10,
        fundingPer = 12,
        targetAmount = 349593453.0,
        targetGoalAmount = 100000000.0
    ),
    invtInfo = investmentInfo(),
    titleSubTitle = listOf(
        TitleSubTitle(
            invtMsg1 = "Major Investors info",
            invtMsg2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magnaaliqua. Ut enim ad minim veniam, quis nostrud exercitation."
        ),
        TitleSubTitle(
            invtMsg1 = "Investment info",
            invtMsg2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magnaaliqua. Ut enim ad minim veniam, quis nostrud exercitation."
        )
    ),
    castCrewDetail = CastCrewDetail(directorName = "ABC", castCrew = listOf(topCast(), topCrew())),
    videoInfo = videoInfo()
)

fun topCast() = CastCrew(
    title = "Top cast",
    casts = listOf(
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        )
    )
)

fun topCrew() = CastCrew(
    title = "Crew",
    casts = listOf(
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict Cumberbatch",
            lName = "Cumberbatch"
        )
    )
)

private fun getThumbnail(videoId: String): String {
    return Uri.parse("https://img.youtube.com/vi")
        .buildUpon()
        .appendPath(videoId)
        .appendPath("hqdefault.jpg")
        .build()
        .toString()
}

fun videoInfo() = listOf(
    VideoInfo(
        id = 1,
        title = "Final Trailer",
        thumbnail = getThumbnail("aWzlQ2N6qqg"),
        videoId = "aWzlQ2N6qqg",
        youtubeUrl = "https://www.youtube.com/watch?v=aWzlQ2N6qqg"
    ),
    VideoInfo(
        id = 1,
        title = "“Dream” Trailer",
        thumbnail = getThumbnail("B9_nql5xBFo"),
        videoId = "B9_nql5xBFo",
        youtubeUrl = "https://www.youtube.com/watch?v=B9_nql5xBFo"
    ),
    VideoInfo(
        id = 1,
        title = "“Dream” Trailer",
        thumbnail = getThumbnail("aWzlQ2N6qqg"),
        videoId = "aWzlQ2N6qqg",
        youtubeUrl = "https://www.youtube.com/watch?v=aWzlQ2N6qqg"
    ),
    VideoInfo(
        id = 1,
        title = "“Dream” Trailer",
        thumbnail = getThumbnail("aWzlQ2N6qqg"),
        videoId = "aWzlQ2N6qqg",
        youtubeUrl = "https://www.youtube.com/watch?v=aWzlQ2N6qqg"
    )
)

fun investmentInfo() = listOf(
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 90000000.0,
        label = "Lorem ipsum",
        subLabel = "Budget"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 14567.0,
        label = "Lorem ipsum",
        subLabel = "Peoples invested"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 0.0,
        label = "Lorem ipsum",
        subLabel = "Public exposure"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 50876.0,
        label = "Lorem ipsum",
        subLabel = "Fund raised info"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 0.0,
        label = "Nadiadwala Grandson Entertainment",
        subLabel = "Production house"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 0.0,
        label = "Lorem ipsum",
        subLabel = "Investment flag"
    )
)