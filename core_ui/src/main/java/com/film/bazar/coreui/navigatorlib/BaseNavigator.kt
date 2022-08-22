package com.film.bazar.coreui.navigatorlib

import android.os.Bundle

/**
 * It provides common actions that can be taken during navigation.
 */
interface BaseNavigator {
    fun openHome()

    fun goBack()

    fun goBack(result: Bundle)

    fun openPage(
        pageId: String,
        addToStack: Boolean = false
    )

    fun openPage(
        pageId: String,
        args: Bundle = Bundle.EMPTY,
        addToStack: Boolean = false,
        forceReload: Boolean = false
    )

    /**
     * This NavigationEntry instance will be used for navigating to other screens.
     * It may navigate to a fragment or activity or dialog,
     * This instance is considered final for navigation
     * This method should not be called from general purpose code.
     */
    @Deprecated("prefer openPage(NavigationRequest)")
    fun openPage(entry: NavigationEntry)

    /**
     * This represents a request for navigation that may resolve to some
     * type of NavigationEntry, i.e an activity, fragment, dialog or external web url
     */
    fun openPage(request: NavigationRequest)

    fun pageNotAccessible()
}