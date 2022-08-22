@file:JvmName("ViewUtils")

package com.film.app.core.utils

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.toggleProgress(show: Boolean) {
  visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Toggle visibility states b/w View.VISIBLE & View.GONE
 */
fun View.toggleVisibility(show: Boolean) {
  require(this !is ProgressBar) { "looks like you want to toggle progress bar, use toggleProgress(show) instead" }
  visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Toggle visibility states b/w View.VISIBLE & View.INVISIBLE
 */
fun View.toggleVisibilityWeak(show: Boolean) {
  visibility = if (show) View.VISIBLE else View.INVISIBLE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.hide() {
  visibility = View.GONE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.show() {
  visibility = View.VISIBLE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.isVisible(): Boolean {
  return visibility == View.VISIBLE
}

@Suppress("NOTHING_TO_INLINE")
inline fun View.isHidden(): Boolean {
  return visibility == View.GONE
}