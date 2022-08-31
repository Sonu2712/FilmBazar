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

fun movieDetail(tab: String): MovieDetail {
    val movieTab = MovieTab.getInstance(tab)
    val invtInfo = if (movieTab.isOnGoingProject())
        investmentInfo()
    else if (movieTab.isPastProject())
        investmentInfoPast()
    else
        investmentInfo()
    return MovieDetail(
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
        invtInfo = invtInfo,
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
        castCrewDetail = CastCrewDetail(
            directorName = "Sam Raimi",
            casts = topCast(),
            crews = topCrew()
        ),
        videoInfo = videoInfo()
    )
}

fun topCast() = CastCrew(
    title = "Top cast",
    casts = listOf(
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Benedict",
            lName = "Cumberbatch",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Elizabeth",
            lName = "Olsen",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "ChiweteI",
            lName = "Ejiofor",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Benedict",
            lName = "Wong",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Xochitl",
            lName = "Gome",
            position = ""
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
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
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Sam Raimi",
            lName = "",
            position = "Directer"
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Michael Waldron",
            lName = "",
            position = "Writer"
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Victoria Alonso",
            lName = "",
            position = "Executive producer"
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Danny Elfman",
            lName = "",
            position = "Musician"
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
            fName = "Bob Murawski",
            lName = "",
            position = "Editor"
        ),
        MovieCastCrew(
            imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
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
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 90000000.0,
        label = "Lorem ipsum",
        subLabel = "Budget"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 14567.0,
        label = "Lorem ipsum",
        subLabel = "Peoples invested"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 0.0,
        label = "Lorem ipsum",
        subLabel = "Public exposure"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 50876.0,
        label = "Lorem ipsum",
        subLabel = "Fund raised info"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 0.0,
        label = "Nadiadwala Grandson Entertainment",
        subLabel = "Production house"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 0.0,
        label = "Lorem ipsum",
        subLabel = "Investment flag"
    )
)

fun investmentInfoPast() = listOf(
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 90000000.0,
        label = "",
        subLabel = "Budget"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 14567.0,
        label = "",
        subLabel = "Peoples invested"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 2000000000.0,
        label = "",
        subLabel = "Revenue generation"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 200000000.0,
        label = "",
        subLabel = "Projected / Net Return"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 900004500.0,
        label = "Nadiadwala Grandson Entertainment",
        subLabel = "Indian market business"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 1900000045000.0,
        label = "",
        subLabel = "Overseas market business"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 50000000.0,
        label = "",
        subLabel = "Satellite rights"
    ),
    InvestmentInfo(
        imageUrl = "https://cdn.pixabay.com/photo/2020/06/30/10/23/icon-5355896__340.png",
        numberValue = 400000000.0,
        label = "",
        subLabel = "OTT revenue"
    )
)