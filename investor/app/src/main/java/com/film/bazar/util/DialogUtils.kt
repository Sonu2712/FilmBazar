@file:JvmName("DialogUtils")

package com.film.bazar.util

import android.content.Context
import com.film.app.core.error.ErrorModel
import com.film.app.core.error.getErrorOnFailure
import com.film.app.core.utils.alert
import timber.log.Timber

fun Context.showError(errorModel: ErrorModel) {
    alert(errorModel.getResolvedMessage(this))
}

fun Context.showError(throwable: Throwable) {
    alert(throwable.getErrorOnFailure().getResolvedMessage(this))
}

fun String.removeSpaces(): String {
    Timber.i("New String ${trim().filter { !it.isWhitespace() }}")
    return trim().filter { !it.isWhitespace() }
}

fun String.appendFaqAtStart(): String {
    return "FAQ_".plus(this.removeSpaces())
}
