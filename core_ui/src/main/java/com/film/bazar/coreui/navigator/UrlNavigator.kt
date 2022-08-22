package com.film.bazar.coreui.navigator

import android.net.Uri

interface UrlNavigator {
    fun openUrl(url: String, addToBackStack: Boolean = true)

    fun openUri(uri: Uri)
}
