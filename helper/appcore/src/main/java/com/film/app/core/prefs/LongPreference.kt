package com.film.app.core.prefs

import android.content.SharedPreferences

class LongPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    private val defaultValue: Long = 0
) : Preference<Long>(preferences, key) {

  fun get(): Long {
    return preferences.getLong(key, defaultValue)
  }

  fun set(value: Long) {
    preferences.edit().putLong(key, value).apply()
  }
}