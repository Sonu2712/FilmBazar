package com.film.bazar.coreui.navigatorlib

import android.os.Bundle

data class NavigationRequest @JvmOverloads constructor(
    @JvmField val pageId: String,
    @JvmField val bundle: Bundle = Bundle.EMPTY,
    /**
     * It is applicable only for fragment navigation
     */
    @JvmField val addToStack: Boolean = false,
    @JvmField val data: Any? = null,
    @JvmField val forceReload: Boolean = false,
    @JvmField val source: String = "User"
)

fun NavigationRequest.asFragmentEntry(): FragmentNavigationEntry {
    val data = this.data
    return if (data is FragmentNavigationEntry) {
        data
    } else {
        FragmentNavigationEntry(
            pageId = pageId,
            args = bundle,
            isAddToStack = addToStack,
            forceReload = forceReload
        )
    }
}