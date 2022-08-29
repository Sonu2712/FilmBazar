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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.OngoingProject
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
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
        tab = MovieTab.PastProjects
    )
)

fun movieDetail(tab : String) = MovieDetail(
    bannerInfo = MovieDetailBannerInfo(
        bannerUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        title = "Doctor Strange in the Multiverse of Madness",
        movieGenre = listOf("Action", "Adventure", "Fantasy")
    ),
    movieFund = MovieFund(
        daysLeft = 10,
        fundingPer = 12,
        targetAmount = 900000000.0,
        targetGoalAmount = 100000000.0
    ),
    invtInfo = if (tab.equals(MovieTab.OngoingProject.toString())) investmentInfo() else investmentInfoPast(),
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
    castCrewDetail = CastCrewDetail(directorName = "Sam Raimi", casts = topCast(), crews = topCrew()),
    videoInfo = videoInfo()
)

fun topCast() = CastCrew(
    title = "Top cast",
    casts = listOf(
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict",
            lName = "Cumberbatch",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Elizabeth",
            lName = "Olsen",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "ChiweteI",
            lName = "Ejiofor",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Benedict",
            lName = "Wong",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Xochitl",
            lName = "Gome",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Rachel",
            lName = "McAdams",
            position = ""
        )
    )
)

fun topCrew() = CastCrew(
    title = "Crew",
    casts = listOf(
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Sam Raimi",
            lName = "",
            position = "Directer"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Michael Waldron",
            lName = "",
            position = "Writer"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Victoria Alonso",
            lName = "",
            position = "Executive producer"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Danny Elfman",
            lName = "",
            position = "Musician"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Bob Murawski",
            lName = "",
            position = "Editor"
        ),
        MovieCastCrew(
            imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
            fName = "Sarah Finn",
            lName = "",
            position = "Casting director "
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

fun investmentInfoPast() = listOf(
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 90000000.0,
        label = "",
        subLabel = "Budget"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 14567.0,
        label = "",
        subLabel = "Peoples invested"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 2000000000.0,
        label = "",
        subLabel = "Revenue generation"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 200000000.0,
        label = "",
        subLabel = "Projected / Net Return"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 900004500.0,
        label = "Nadiadwala Grandson Entertainment",
        subLabel = "Indian market business"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 1900000045000.0,
        label = "",
        subLabel = "Overseas market business"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 50000000.0,
        label = "",
        subLabel = "Satellite rights"
    ),
    InvestmentInfo(
        imageUrl = "https://images.indianexpress.com/2021/12/strange.jpg",
        numberValue = 400000000.0,
        label = "",
        subLabel = "OTT revenue"
    )
)