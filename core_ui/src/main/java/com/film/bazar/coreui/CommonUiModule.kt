package com.film.bazar.coreui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.film.app.core.prefs.IntegerPreference
import com.film.app.core.prefs.StringPreference
import com.film.annotations.DayOrNight
import com.film.annotations.UserLanguage
import dagger.Module
import dagger.Provides

@Module
object CommonUiModule {
    const val PREF_IS_DAY = "is_day"

    @Provides
    @DayOrNight
    fun provideDayNightMode(prefs: SharedPreferences): IntegerPreference {
        return IntegerPreference(prefs, PREF_IS_DAY, AppCompatDelegate.MODE_NIGHT_NO)
    }

    @UserLanguage
    @Provides
    fun provideUserLanguage(prefs: SharedPreferences): StringPreference {
        return StringPreference(prefs, LocaleManager.PREF_LANGUAGE, "en")
    }
}