package com.film.bazar.data.drawermenu

import com.film.app.core.prefs.BooleanPreference
import com.film.bazar.domain.drawermenu.BaseMenu
import com.film.bazar.domain.drawermenu.ChildMenu

fun getMockMenuItems(
    userType: String,
    valuePack: BooleanPreference,
    branchClient: BooleanPreference
): List<BaseMenu> {
    val menuList = mutableListOf<BaseMenu>()
    when (userType) {
        "M" -> {
            menuList.addAll(getMFUserMenu(valuePack, branchClient))
        }
        "T" -> {
            menuList.addAll(getCustUserMenu(valuePack, branchClient))
        }
        "G" -> {
            menuList.addAll(getGuestMenu())
        }
        "OU" -> {
            menuList.addAll(getOpenUserMenu())
        }
        "N" -> {
            menuList.addAll(getSSOMenu(valuePack, branchClient))
        }
    }
    return menuList.toList()
}

fun getOpenUserMenu(): List<BaseMenu> {
    return listOf(
        getWatchlist(false),
        getMarkets(),
        getInvestMF(false),
        getInvestNow(false),
        getStockAlert(false),
        getMyReports(false),
        getNews(),
        getFundTransfer(false),
        getOurRecommendation(false),
        getLearnMenu(false),
        getReferNEarn(),
        getOtherEssentialsOpenUser(),
        getChat(false),
        getAboutUs(),
        getSwitchToTrader()
    )
}

fun getGuestMenu(): List<BaseMenu> {
    return listOf(
        getWatchlist(true),
        getMarkets(),
        getInvestMF(false),
        getInvestNow(false),
        getStockAlert(false),
        getMyReports(false),
        getNews(),
        getFundTransfer(false),
        getOurRecommendation(false),
        getGuestLearnMenu(),
        getReferNEarn(),
        getGuestOtherEssentials(),
        getChat(false),
        getSwitchToTrader(),
        logout()
    )
}

fun getSSOMenu(
    valuePack: BooleanPreference,
    branchClient: BooleanPreference
): List<BaseMenu> {
    return listOf(
        getWatchlist(true),
        getMarkets(),
        getInvestMF(true),
        getInvestNow(true),
        getStockAlert(true),
        getMyReports(true),
        getNews(),
        getFundTransfer(true),
        getOurRecommendation(true),
        getLearnMenu(true),
        getReferNEarn(),
        getOtherEssentials(true, valuePack, branchClient),
        getChat(true),
        getSwitchToTrader(),
        logout()
    )
}

fun getCustUserMenu(
    valuePack: BooleanPreference,
    branchClient: BooleanPreference
): List<BaseMenu> {
    return listOf(
        getWatchlist(true),
        getMarkets(),
        getInvestMF(true),
        getInvestNow(true),
        getStockAlert(true),
        getMyReports(true),
        getNews(),
        getFundTransfer(true),
        getOurRecommendation(true),
        getLearnMenu(true),
        getReferNEarn(),
        getOtherEssentials(true, valuePack, branchClient),
        getChat(true),
        getSwitchToTrader(),
        logout()
    )
}

fun getMFUserMenu(
    valuePack: BooleanPreference,
    branchClient: BooleanPreference
): List<BaseMenu> {
    return listOf(
        getWatchlist(true),
        getMarkets(),
        getInvestMF(true),
        getMFDInvestNow(),
        getStockAlert(true),
        getMFDMyReports(),
        getNews(),
        getFundTransfer(false),
        getOurRecommendation(true),
        getLearnMenu(true),
        getReferNEarn(),
        getOtherEssentials(false, valuePack, branchClient),
        getChat(true),
        getSwitchToTrader(),
        logout()
    )
}

private fun getMarkets(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Markets",
        children = listOf(
            ChildMenu(
                label = "Equity",
                pageId = "marketequity"
            ),
            ChildMenu(
                label = "Derivatives",
                pageId = "marketfno"
            ),
            ChildMenu(
                label = "Currency",
                pageId = "marketcurrency"
            ),
            ChildMenu(
                label = "Commodity",
                pageId = "marketcommodity"
            )
        )
    )
}

private fun getMFDMyReports(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Reports",
        enabled = true,
        children = listOf(
            ChildMenu(
                enabled = true,
                label = "Research Reports",
                pageId = "researchreport"
            ),
            ChildMenu(
                label = "Trading Reports",
                enabled = false,
                pageId = "tradingreport"
            ),
            ChildMenu(
                label = "Demat Holdings",
                enabled = true,
                pageId = "dematholdings"
            ),
            ChildMenu(
                label = "Operational Reports",
                enabled = true,
                pageId = "operationalreport"
            ),
            ChildMenu(
                label = "Mutual Fund Reports",
                enabled = true,
                pageId = "mfreports"
            ),
            ChildMenu(
                label = "Ledger",
                enabled = false,
                pageId = "ledgersummary"
            ),
            ChildMenu(
                label = "Contract Note",
                enabled = false,
                pageId = "contractnotes"
            ),
            ChildMenu(
                label = "Stock SIP Order Book",
                enabled = false,
                pageId = "stocksiporderbook"
            ),
            ChildMenu(
                label = "Family Reports",
                enabled = true,
                pageId = "familyreport"
            ),
            ChildMenu(
                label = "Circulars",
                enabled = true,
                pageId = "circular"
            )
        )
    )
}

private fun getMyReports(enable: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Reports",
        enabled = true,
        children = listOf(
            ChildMenu(
                enabled = enable,
                label = "Research Reports",
                pageId = "researchreport"
            ),
            ChildMenu(
                label = "Trading Reports",
                enabled = enable,
                pageId = "tradingreport"
            ),
            ChildMenu(
                label = "Demat Holdings",
                enabled = enable,
                pageId = "dematholdings"
            ),
            ChildMenu(
                label = "Operational Reports",
                enabled = enable,
                pageId = "operationalreport"
            ),
            ChildMenu(
                label = "Mutual Fund Reports",
                enabled = enable,
                pageId = "mfreports"
            ),
            ChildMenu(
                label = "Ledger",
                enabled = enable,
                pageId = "ledgersummary"
            ),
            ChildMenu(
                label = "Contract Note",
                enabled = enable,
                pageId = "contractnotes"
            ),
            ChildMenu(
                label = "Stock SIP Order Book",
                enabled = enable,
                pageId = "stocksiporderbook"
            ),
            ChildMenu(
                label = "Family Reports",
                enabled = enable,
                pageId = "familyreport"
            ),
            ChildMenu(
                label = "Circulars",
                enabled = enable,
                pageId = "circular"
            )
        )
    )
}

private fun getOtherEssentialsOpenUser(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Other Essentials",
        children = listOf(
            /*ChildMenu(
                enabled = false,
                label = R.string.app_menu_label_offline_mf_redemption,
                pageId = MutualFundNavigationConstants.NAVIGATE_TO_MF_OFFLINE_REDEMPTION_FRAGMENT
            ),*/
            ChildMenu(
                label = "MO Credits",
                enabled = false,
                pageId = "moCredits"
            ),
            ChildMenu(
                label = "Value Pack - Stop Renewal",
                enabled = false,
                pageId = "valuepackstoprenewal"
            ),
            ChildMenu(
                label = "Pro-Partner Dashboard",
                enabled = false,
                pageId = "propartnerdashboard"
            ),
            ChildMenu(
                label = "Order Confirmation",
                enabled = false,
                pageId = "orderconfirmation"
            ),
            ChildMenu(
                label = "Call To Trade",
                enabled = false,
                pageId = "callToTrade"
            )
        )
    )
}

private fun getGuestOtherEssentials(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Other Essentials",
        enabled = true,
        children = listOf(
            ChildMenu(
                label = "MO Credits",
                enabled = false,
                pageId = "moCredits"
            ),
            ChildMenu(
                label = "Value Pack - Stop Renewal",
                enabled = false,
                pageId = "valuepackstoprenewal"
            ),
            ChildMenu(
                label = "Pro-Partner Dashboard",
                enabled = false,
                pageId = "propartnerdashboard"
            ),
            ChildMenu(
                label = "Order Confirmation",
                enabled = false,
                pageId = "orderconfirmation"
            ),
            ChildMenu(
                label = "Call To Trade",
                enabled = false,
                pageId = "callToTrade"
            ),
            ChildMenu(
                label = "About Us",
                enabled = true,
                pageId = "aboutus"
            ),
            ChildMenu(
                label = "Restructure Portfolio",
                enabled = true,
                pageId = "prpdf"
            )
        )
    )
}

private fun getOtherEssentials(
    enabled: Boolean,
    valuePack: BooleanPreference,
    branchClient: BooleanPreference
): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Other Essentials",
        enabled = true,
        children = getOtherEssentialsChildMenuList(enabled, valuePack.get(), branchClient.get())

    )
}

private fun getOtherEssentialsChildMenuList(
    enabled: Boolean,
    isSubscribedToValuePack: Boolean,
    isBranchClient: Boolean
): List<ChildMenu> {
    return mutableListOf<ChildMenu>().apply {
        add(
            ChildMenu(
                label = "MO Credits",
                enabled = enabled,
                pageId = "moCredits"
            )
        )
        if (isSubscribedToValuePack)
            add(
                ChildMenu(
                    label = "Value Pack - Stop Renewal",
                    enabled = enabled,
                    pageId = "valuepackstoprenewal"
                )
            )
        if (isBranchClient)
            add(
                ChildMenu(
                    label = "Pro-Partner Dashboard",
                    enabled = enabled,
                    pageId = "propartnerdashboard"
                )
            )
        add(
            ChildMenu(
                label = "Order Confirmation",
                enabled = enabled,
                pageId = "orderconfirmation"
            )
        )
        add(
            ChildMenu(
                label = "Call To Trade",
                enabled = enabled,
                pageId = "callToTrade"
            )
        )
        add(
            ChildMenu(
                label = "About Us",
                enabled = true,
                pageId = "aboutus"
            )
        )
    }
}

private fun getChat(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        enabled = enabled,
        label = "Chat With Us",
        pageId = "chatwithus"
    )
}

private fun getAboutUs(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "About Us",
        pageId = "aboutus"
    )
}

private fun getMFDInvestNow(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Invest Now",
        enabled = true,
        children = listOf(
            ChildMenu(
                label = "Intelligent Advisory Portfolio’s",
                enabled = false,
                pageId = "aceproducts"
            ),
            ChildMenu(
                label = "Invest in ME Gold",
                enabled = false,
                pageId = "megold"
            ),
            ChildMenu(
                label = "Suggest Me Tool (SMT)",
                enabled = false,
                pageId = "suggestme"
            ),
            ChildMenu(
                label = "IPO / OFS / BuyBack / Bonds / FD",
                enabled = true,
                pageId = "ipo"
            ),
            ChildMenu(
                label = "Bulk Order",
                enabled = false,
                pageId = "bulkorder"
            ),
            ChildMenu(
                label = "Stock SIP",
                enabled = false,
                pageId = "stocksiphome"
            ),
            ChildMenu(
                label = "Insurance",
                enabled = true,
                pageId = "insurance"
            ),
            ChildMenu(
                label = "Invest in US Equities",
                enabled = true,
                pageId = "usEquities"
            ),
            ChildMenu(
                label = "Flexi Loan",
                enabled = false,
                pageId = "flexiLoan"
            )
        )
    )
}

private fun getInvestNow(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Invest Now",
        enabled = true,
        children = listOf(
            ChildMenu(
                label = "Intelligent Advisory Portfolio’s",
                enabled = enabled,
                pageId = "aceproducts"
            ),
            ChildMenu(
                label = "Invest in ME Gold",
                enabled = true,
                secure = true,
                pageId = "megold"
            ),
            ChildMenu(
                label = "Suggest Me Tool (SMT)",
                enabled = true,
                pageId = "suggestme"
            ),
            ChildMenu(
                label = "IPO / OFS / BuyBack / Bonds / FD",
                enabled = enabled,
                pageId = "ipo"
            ),
            ChildMenu(
                label = "Bulk Order",
                enabled = enabled,
                pageId = "bulkorder"
            ),
            ChildMenu(
                label = "Stock SIP",
                enabled = enabled,
                pageId = "stocksiphome"
            ),
            ChildMenu(
                label = "Insurance",
                enabled = enabled,
                pageId = "insurance"
            ),
            ChildMenu(
                label = "Invest in US Equities",
                enabled = enabled,
                pageId = "usEquities"
            ),
            ChildMenu(
                label = "Flexi Loan",
                enabled = enabled,
                pageId = "flexiLoan"
            )
        )
    )
}

private fun getNews(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "News",
        pageId = "newshome"
    )
}

private fun getFundTransfer(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        enabled = enabled,
        label = "Fund Transfer",
        pageId = "fundtransfer"
    )
}

private fun getOurRecommendation(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        enabled = enabled,
        label = "Advice",
        pageId = "recommendation"
    )
}

private fun getLearnMenu(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Learn",
        children = listOf(
            ChildMenu(
                label = "EDUMO",
                enabled = enabled,
                pageId = "education"
            ),
            ChildMenu(
                label = "Webinar",
                enabled = enabled,
                pageId = "webinar"
            ),
            ChildMenu(
                label = "MOSt Guide",
                enabled = enabled,
                pageId = "mostGuide"
            )
        )
    )
}

private fun getGuestLearnMenu(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Learn",
        children = listOf(
            ChildMenu(
                label = "EDUMO",
                enabled = false,
                pageId = "education"
            ),
            ChildMenu(
                label = "Webinar",
                enabled = true,
                pageId = "webinar"
            ),
            ChildMenu(
                label = "MOSt Guide",
                enabled = false,
                pageId = "mostGuide"
            )
        )
    )
}

private fun getReferNEarn(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Refer and Earn",
        pageId = "refernearn"
    )
}

private fun getSwitchToTrader(): BaseMenu {
    return BaseMenu(
        icon = "",
        label = "Switch to MO Trader",
        pageId = "motrader"
    )
}

private fun getStockAlert(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Alerts",
        enabled = enabled,
        pageId = "stockalert"
    )
}

private fun getSwitchToTejiMandi(): BaseMenu {
    return BaseMenu(
        icon = "",
        label = "Switch to Teji Mandi",
        pageId = "tejimandi"
    )
}

private fun getWatchlist(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        enabled = enabled,
        label = "Watchlist",
        pageId = "watchlisthome"
    )
}

private fun getInvestMF(enabled: Boolean): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        enabled = enabled,
        label = "Invest In MF",
        pageId = "mfhome"
    )
}

private fun logout(): BaseMenu {
    return BaseMenu(
        icon = "https://invest.motilaloswal.com/investorimages/FundWithBestReturns.png",
        label = "Logout",
        pageId = "logout"
    )
}