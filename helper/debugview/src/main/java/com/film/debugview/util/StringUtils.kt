@file:JvmName("StringUtils")

package com.film.debugview.util

fun String.truncateAt(
    length: Int
): String {
  return if (this.length > length) this.substring(0, length) else this
}