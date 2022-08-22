package com.film.bazar.coreui.navigatorlib

import androidx.fragment.app.Fragment

/**
 * Created by snehaganapuram on 02-01-2018.
 */

interface Navigator {
    fun getContainerViewId(): Int

    fun getFragment(pageId: String): Fragment?

    fun authorizationDialog(listener: (AuthorizationState) -> Unit)

    fun setAppTitle(
        page: String,
        appTitle: AppTitle?
    )

    fun hideAppTitle()

    fun clearTitles()

    fun gotoPage(fragmentEntry: FragmentNavigationEntry)

    fun isConnected(): Boolean

    fun showProgress()

    fun hideProgress()
}
