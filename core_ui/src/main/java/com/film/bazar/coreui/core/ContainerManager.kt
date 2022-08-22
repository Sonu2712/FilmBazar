package com.film.bazar.coreui.core

import android.view.View
import com.film.bazar.coreui.navigatorlib.AppTitle
import com.film.bazar.coreui.navigatorlib.NavigationElement
import com.film.bazar.coreui.navigatorlib.Navigator
import java.io.Serializable

interface ContainerManager : Navigator {
    fun setState(page: String, state: ContainerState)
    fun onBackNavigation()
}

interface ContainerChild : NavigationElement {
    val containerState: ContainerState
}

interface CoachMarksHelper {
    fun getRootView(): View
}

data class ContainerState(
    @JvmField val showBackButton: Boolean = false,
    @JvmField val showToolbar: Boolean = true,
    @JvmField val showBottomBar: Boolean = true,
    @JvmField val appTitle: AppTitle = AppTitle.EMPTY,
    @JvmField @Transient val    toolbarView: View? = null,
    @JvmField val showShadow: Boolean = true,
    @JvmField val showNavigationIcon: Boolean = true,
    @JvmField val showPinkAssit: Boolean = true
    ) : Serializable {
    companion object {
        @JvmField
        val DEFAULT = ContainerState()

        @JvmField
        val HideBars = ContainerState(showToolbar = false, showBottomBar = false)

        fun detailPage(): ContainerState {
            return showBackNavigation()
        }

        fun showBackNavigation(showBottomBar: Boolean = false): ContainerState {
            return ContainerState(
                showBackButton = true,
                showToolbar = true,
                showBottomBar = showBottomBar
            )
        }
    }
}
