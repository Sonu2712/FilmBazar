package com.film.app.core.prefs

import android.content.SharedPreferences

class StringPreference @JvmOverloads constructor(
    preferences: SharedPreferences,
    key: String,
    private val defaultValue: String? = null
) : Preference<Boolean>(preferences, key) {

  fun get(): String {
    return preferences.getString(key, defaultValue) ?: ""
  }

  fun set(value: String) {
    preferences.edit().putString(key, value).apply()
  }
}