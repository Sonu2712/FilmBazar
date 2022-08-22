package com.film.app.core.data

import android.content.Context
import androidx.annotation.StringRes

/**
 * This model is used to display a list with two columns
 * and no extra data is needed for any purpose
 */
data class KeyValueModel @JvmOverloads constructor(
    private val key: String? = null,
    @StringRes private val keyRes: Int = 0,
    val value: String
) {
  fun getKey(context: Context): String {
    return key ?: context.getString(keyRes)
  }
}