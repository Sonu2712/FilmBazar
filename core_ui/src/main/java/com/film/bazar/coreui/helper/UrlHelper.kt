package com.film.bazar.coreui.helper

import android.net.Uri

fun Uri.isDeepLink(): Boolean {
    return scheme == "film" && host == "filmbazar.app"
}

fun Uri.isEkycDeepLink(): Boolean {
    return scheme == "film" && host == "filmbazar.app"
}

fun String.isDeepLink(): Boolean {
    return this.startsWith("film://filmbazar.app", true)
}

fun String.isPinkAssit():Boolean{
    return  this.contains("pinkAssistId")
}

fun String.isWebLink(): Boolean {
    val urlRegex =
        ("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?\$")
            .toRegex()
    return urlRegex.matches(this)
}