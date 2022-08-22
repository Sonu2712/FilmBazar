package com.film.app.core.prefs

import android.content.SharedPreferences

abstract class Preference<T>(
    @JvmField protected val preferences: SharedPreferences,
    @JvmField protected val key: String
) {
  val isSet: Boolean
    get() = preferences.contains(key)

  val isNotSet: Boolean
    get() = !isSet

  fun delete() {
    preferences.edit().remove(key).apply()
  }
}