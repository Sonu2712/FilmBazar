package com.film.bazar.data.drawermenu

import com.film.bazar.domain.drawermenu.StartupScreen
import com.film.bazar.domain.drawermenu.UBottomBarMenu


val HOME = UBottomBarMenu(
    "home",
    "Home",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = true,
    isSelected = true
)
val PORTFOLIODAYZERO = UBottomBarMenu(
    "portfoliodayzero",
    "Portfolio",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = true,
    isSelected = true
)

val PORTFOLIO = UBottomBarMenu(
    "portfolio",
    "Portfolio",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = true,
    isSelected = true
)

val SEARCH = UBottomBarMenu(
    "search",
    "Search",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = true,
    isSelected = true
)
val WATCHLIST = UBottomBarMenu(
    "watchlisthome",
    "Watchlist",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = true
)
val MF = UBottomBarMenu(
    "mfhome",
    "Mutual Fund",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = true
)
val TRADING_REPORT = UBottomBarMenu(
    "tradingreport",
    "Reports",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = false
)
val NEWS = UBottomBarMenu(
    "newshome",
    "News",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = false
)
val MARKET = UBottomBarMenu(
    "marketequity",
    "Markets",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = false
)
val RESEARCH_REPORT = UBottomBarMenu(
    "ledgersummary",
    "Ledger",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = false
)

val OUR_RECOMMENDATION = UBottomBarMenu(
    "recommendation",
    "Advice",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = false
)
val SMT = UBottomBarMenu(
    "suggestme",
    "SMT",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = false,
    isSelected = true
)
val EDU_MORE = UBottomBarMenu(
    "education",
    "EDUMO",
    "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
    isDefault = true,
    isSelected = true
)

val PostLoginBottomBarMenusWithPortfolioDayZero: List<UBottomBarMenu> = listOf(
    HOME,
    PORTFOLIODAYZERO,
    SEARCH,
    WATCHLIST,
    MF,
    TRADING_REPORT,
    NEWS,
    MARKET,
    RESEARCH_REPORT,
    OUR_RECOMMENDATION
)

val PostLoginBottomBarMenus: List<UBottomBarMenu> = listOf(
    HOME,
    PORTFOLIO,
    SEARCH,
    WATCHLIST,
    MF,
    TRADING_REPORT,
    NEWS,
    MARKET,
    RESEARCH_REPORT,
    OUR_RECOMMENDATION
)

val PreLoginBottomBarMenus: List<UBottomBarMenu> = listOf(
    NEWS,
    EDU_MORE,
    SEARCH,
    MARKET,
    SMT
).map { it.copy(isDefault = true) }


fun getBottomBarMenu(isLoggedIn: Boolean, hasPortfolio: Boolean): List<UBottomBarMenu> {
    return when {
        isLoggedIn -> {
            when {
                hasPortfolio -> PostLoginBottomBarMenus
                else -> PostLoginBottomBarMenusWithPortfolioDayZero
            }
        }
        else -> {
            PreLoginBottomBarMenus
        }
    }
}

fun getBottomBarStartUpScreenList(hasPortfolio: Boolean) =
    listOf(
        StartupScreen("home", "Home"),
        StartupScreen(
            if (hasPortfolio) "portfolio" else "portfoliodayzero",
            "Portfolio"
        ),
        StartupScreen(
            "watchlisthome",
            "watchlist"
        ),
        StartupScreen(
            "marketequity",
           "Markets"
        ),
        StartupScreen(
            "tradingreport",
            "Trading Reports"
        )
    )