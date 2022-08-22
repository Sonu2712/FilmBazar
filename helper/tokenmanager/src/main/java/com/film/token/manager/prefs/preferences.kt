package com.film.token.manager.prefs

import com.film.app.core.prefs.StringPreference

fun StringPreference.getOrDefault(default: String): String {
  return get()
}

val StringPreference.isValid: Boolean
  get() {
    return isSet && get().isNotEmpty()
  }