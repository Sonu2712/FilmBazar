package com.film.bazar.bottombar

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
        MENU_MORE -> NavigationConstants.NAVIGATE_TO_MORE
        else -> NavigationConstants.NAVIGATE_TO_HOME
    }
}

const val MENU_HOME = 100
const val MENU_PORTFOLIO = 101
const val MENU_WALLET = 102
const val MENU_MORE = 103