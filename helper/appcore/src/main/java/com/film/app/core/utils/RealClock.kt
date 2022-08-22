package com.film.app.core.utils

import android.os.SystemClock
import com.film.commons.utils.Clock

object RealClock : Clock {
  override fun getTimestamp(): Long {
    return SystemClock.elapsedRealtime()
  }
}