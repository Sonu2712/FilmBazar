package com.film.bazar.coreui

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LocaleManager {
    const val PREF_LANGUAGE = "prefLanguageSettingValue"

    @JvmStatic
    fun withLocale(c: Context): Context {
        return updateResources(c, getLanguage(c))
    }

    @JvmStatic
    fun getLanguage(c: Context): String {
        val preferences = c.getSharedPreferences(c.packageName, Context.MODE_PRIVATE)
        return preferences.getString(PREF_LANGUAGE, "en") ?: "en"
    }

    fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources

        val config = Configuration(res.configuration)
        config.setLocale(locale)
        if(language=="ta") {
            config.fontScale = 1.0f
        }
      return  context.createConfigurationContext(config)
    }
}

fun Context.localised(): Context {
    val desiredLocale = Locale(LocaleManager.getLanguage(this))
    val conf = Configuration(resources.configuration).apply {
        setLocale(desiredLocale)
    }
    return createConfigurationContext(conf)
}