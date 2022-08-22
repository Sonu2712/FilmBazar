package com.film.token.manager.prefs
import android.content.SharedPreferences
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.PrefStore
import com.film.app.core.prefs.StringPreference

data class TokenPrefStore(
    @JvmField val loginTime: LongPreference,
    @JvmField val tokenCreationTime: LongPreference,
    @JvmField val accessToken: StringPreference,
    @JvmField val tokenValidity: LongPreference,
    @JvmField val refreshToken: StringPreference,
    @JvmField val reLoginDuration: LongPreference
) : PrefStore {
  override fun clear() {
    loginTime.delete()
    tokenCreationTime.delete()
    accessToken.delete()
    tokenValidity.delete()
    refreshToken.delete()
    reLoginDuration.delete()
  }

  companion object {
    const val PREF_LOGIN_DEVICE_TIME = "last_login_device_time"
    const val PREF_TOKEN_VALIDITY = "token_validity_time"
    const val PREF_TOKEN_CREATED_TIME = "token_creation_time"
    const val PREF_ACCESS_TOKEN = "access_token"
    const val PREF_REFRESH_TOKEN = "refresh_token"
    const val PREF_RE_LOGIN_DURATION = "re_login_duration"

    fun createTokenPrefStore(
        preference: SharedPreferences,
        safePreferences: SharedPreferences
    ): TokenPrefStore {
      return TokenPrefStore(
          loginTime = LongPreference(preference, PREF_LOGIN_DEVICE_TIME, 0),
          tokenCreationTime = LongPreference(preference, PREF_TOKEN_CREATED_TIME, 0),
          accessToken = StringPreference(safePreferences, PREF_ACCESS_TOKEN),
          tokenValidity = LongPreference(preference, PREF_TOKEN_VALIDITY, 0),
          refreshToken = StringPreference(safePreferences, PREF_REFRESH_TOKEN),
          reLoginDuration = LongPreference(preference, PREF_RE_LOGIN_DURATION, 0)
      )
    }
  }
}