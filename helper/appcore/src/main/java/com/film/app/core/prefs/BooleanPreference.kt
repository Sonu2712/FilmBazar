package com.film.app.core.prefs

import android.content.SharedPreferences

class BooleanPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    private val defaultValue: Boolean = false
) : Preference<Boolean>(preferences, key) {

  fun get(): Boolean {
    return preferences.getBoolean(key, defaultValue)
  }

  fun set(value: Boolean) {
    preferences.edit().putBoolean(key, value).apply()
  }
}
