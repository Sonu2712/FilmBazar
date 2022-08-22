package com.film.bazar.appuser.helper

import android.content.SharedPreferences
import com.film.app.core.prefs.IntegerPreference
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.PrefStore
import com.film.app.core.prefs.StringPreference

data class UserPrefStore(
    @JvmField val userInfo: StringPreference,
    @JvmField val credential: StringPreference
) : PrefStore {
  override fun clear() {
    userInfo.delete()
    credential.delete()
  }
}

object PrefStoreProvider {
  const val PREF_USER_IDENTITY = "app_user_identity"
  const val PREF_CLIENT_CODE = "client_code"
  const val PREF_DOB = "user_dob"
  const val PREF_TLOGIN_INFO = "tlogin_info"
  const val PREF_MULTI_FACTOR_TIME = "multi_factor_time"
  const val PREF_VERSION_NO = "version_no"

  const val PREF_USER_INFO = "user_info"
  const val PREF_USER_CRED = "user_cred"

  fun createUserPrefStore(
      safePreference: SharedPreferences
  ): UserPrefStore {
    return UserPrefStore(
        userInfo = StringPreference(
            safePreference,
            PREF_USER_INFO
        ),
        credential = StringPreference(
            safePreference,
            PREF_USER_CRED
        )
    )
  }

  fun createLoginPrefStore(
      preference: SharedPreferences,
      safePreference: SharedPreferences
  ): LoginPrefStore {
    return LoginPrefStore(
        userIdentity = StringPreference(
            preference,
            PREF_USER_IDENTITY,
            "MGUEST"
        ),
        clientCode = StringPreference(
            preference,
            PREF_CLIENT_CODE,
            "MGUEST"
        ),
        multiFactorInfo = StringPreference(
            safePreference,
            PREF_DOB
        ),
        tLoginInfo = StringPreference(
            safePreference,
            PREF_TLOGIN_INFO
        ),
        multiFactorTime = LongPreference(
            preference,
            PREF_MULTI_FACTOR_TIME,
            0
        ),
        interactiveVersion = IntegerPreference(
            preference,
            PREF_VERSION_NO,
            19
        )
    )
  }
}
