package com.film.bazar.drawermenu.data

import com.film.bazar.R
import com.film.bazar.constants.NavigationConstants

fun String.getIcon() : Int {
    return when {
        this == NavigationConstants.NAVIGATE_TO_LOGOUT -> R.drawable.app_ic_logout
        this == NavigationConstants.NAVIGATE_TO_HOME -> R.drawable.app_ic_home
        this == NavigationConstants.NavigationPageIdentifier.PAGE_IDENTIFIER_OTHER_ESSENTIALS -> R.drawable.app_ic_other_essentials
        this == NavigationConstants.NavigationPageIdentifier.PAGE_IDENTIFIER_INVEST_NOW -> R.drawable.app_ic_help_me_invest
        this == NavigationConstants.NavigationPageIdentifier.PAGE_IDENTIFIER_LEARN -> R.drawable.app_ic_learn
        this == NavigationConstants.NavigationPageIdentifier.PAGE_IDENTIFIER_REPORTS -> R.drawable.app_ic_reports
        this == NavigationConstants.NavigationPageIdentifier.PAGE_IDENTIFIER_MARKETS -> R.drawable.app_ic_market
        else -> R.drawable.app_ic_home
    }
}