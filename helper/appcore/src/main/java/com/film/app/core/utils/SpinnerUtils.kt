@file:JvmName("SpinnerUtils")

package com.film.app.core.utils

import android.widget.Spinner

fun Spinner?.toggleSpinnerState(enable: Boolean) {
  if (this == null) return
  isEnabled = enable
  alpha = if (enable) 1.0f else 0.5f
}