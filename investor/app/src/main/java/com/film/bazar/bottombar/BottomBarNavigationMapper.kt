package com.film.bazar.bottombar

import com.film.bazar.R
import com.film.bazar.constants.NavigationConstants

fun String.bottomBarIntMapper(): Int {
    return when (this) {
        NavigationConstants.NAVIGATE_TO_HOME -> MENU_HOME
        NavigationConstants.NAVIGATE_TO_PORTFOLIO -> MENU_PORTFOLIO
        NavigationConstants.NAVIGATE_TO_WALLET -> MENU_WALLET
        NavigationConstants.NAVIGATE_TO_MORE -> MENU_MORE
        else -> MENU_HOME
    }
}

fun Int.bottomBarStringMapper(): String {
    return when (this) {
        MENU_HOME -> NavigationConstants.NAVIGATE_TO_HOME
        MENU_PORTFOLIO -> NavigationConstants.NAVIGATE_TO_PORTFOLIO
        MENU_WALLET -> NavigationConstants.NAVIGATE_TO_WALLET
        MENU_MORE -> NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT
        else -> NavigationConstants.NAVIGATE_TO_HOME
    }
}

fun String.getIcon() : Int {
    return when {
        this == NavigationConstants.NAVIGATE_TO_LOGOUT -> R.drawable.ic_check_circle_green
        this == NavigationConstants.NAVIGATE_TO_HOME -> R.drawable.ic_check_circle_green
        this ==  NavigationConstants.NAVIGATE_TO_PORTFOLIO -> R.drawable.ic_check_circle_green
        this ==  NavigationConstants.NAVIGATE_TO_WALLET -> R.drawable.ic_check_circle_green
        this ==  NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT -> R.drawable.ic_check_circle_green
        else -> R.drawable.ic_check_circle_green
    }
}

const val MENU_HOME = 100
const val MENU_PORTFOLIO = 101
const val MENU_WALLET = 102
const val MENU_MORE = 103