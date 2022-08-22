package com.film.app.core.prefs

import android.content.SharedPreferences

/**
 * Created by snehaganapuram on 8/17/2017.
 */

class StringListPreference(
    preferences: SharedPreferences,
    key: String,
    defaultValue: List<String>
) : Preference<List<String>>(preferences, key) {
  private val defaultValue: String = defaultValue.joinToString(",")

  fun set(values: List<String>) {
    preferences.edit().putString(key, values.joinToString(",")).apply()
  }

  fun get(): List<String> {
    val string = preferences.getString(key, defaultValue)
    return string?.split(",")?.filter(String::isEmpty).orEmpty()
  }
}
