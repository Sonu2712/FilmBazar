@file:JvmName("StringUtils")

package com.film.app.core.utils

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.text.NumberFormat
import java.text.ParsePosition
import kotlin.text.Charsets.UTF_8

fun String.toHtmlText(): Spanned {
  return if (VERSION.SDK_INT >= VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
  } else {
    Html.fromHtml(this)
  }
}

fun Context.getEncodedIPAddress(): String {
  return Base64.encodeToString(getUuid().toByteArray(UTF_8),
      Base64.DEFAULT)
}

fun String?.isNumeric(): Boolean {
  if (this.isNullOrBlank()) return false
  val pos = ParsePosition(0)
  NumberFormat.getInstance().parse(this, pos)
  return this.length == pos.index
}

fun String.toProperCase(): String {
  return substring(0, 1).toUpperCase() + substring(1)
}