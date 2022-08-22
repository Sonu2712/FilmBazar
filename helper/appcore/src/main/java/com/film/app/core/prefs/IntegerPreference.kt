package com.film.app.core.prefs

import android.content.SharedPreferences

class IntegerPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    private val defaultValue: Int = 0
) : Preference<Int>(preferences, key) {

  fun get(): Int {
    return preferences.getInt(key, defaultValue)
  }

  fun set(value: Int) {
    preferences.edit().putInt(key, value).apply()
  }
}
