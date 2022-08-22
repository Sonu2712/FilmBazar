package com.film.bazar.appuser.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.film.app.core.prefs.PrefStore
import com.film.bazar.appuser.helper.LoginPrefStore
import com.film.bazar.appuser.helper.PrefStoreProvider
import com.film.bazar.appuser.helper.PrefStoreProvider.PREF_CLIENT_CODE
import com.film.bazar.appuser.helper.UserPrefStore
import com.film.bazar.appuser.meta.SafePreference
import mosl.rx_preferences.Preference
import mosl.rx_preferences.RxSharedPreferences

interface UserDataStore {
    fun loginPrefStore(): LoginPrefStore
    fun rxClientCode(): Preference<String>
    fun getIntPreference(key: String, defaultValue: Int = 0): Preference<Int>
    fun getLongPreference(key: String, defaultValue: Long = 0L): Preference<Long>
    fun getStringPreference(key: String, defaultValue: String = ""): Preference<String>
    fun getBooleanPreference(key: String, defaultValue: Boolean = false): Preference<Boolean>
    fun <T> getPreference(key: String, defaultValue: T): Preference<T>
}

class UserDataStoreImpl internal constructor(
    private val preference: SharedPreferences,
    @SafePreference private val safePreference: SharedPreferences,
    private val gson: Gson
) : UserDataStore, PrefStore {
    private val rxPreference: RxSharedPreferences = RxSharedPreferences.create(preference)
    private val loginPrefStore: LoginPrefStore =
        PrefStoreProvider.createLoginPrefStore(preference, safePreference)
    internal val userPrefStore: UserPrefStore =
        PrefStoreProvider.createUserPrefStore(safePreference)

    override fun loginPrefStore(): LoginPrefStore {
        return loginPrefStore
    }

    override fun rxClientCode(): Preference<String> {
        return getStringPreference(PREF_CLIENT_CODE, "MGUEST")
    }

    override fun getIntPreference(key: String, defaultValue: Int): Preference<Int> {
        return rxPreference.getInteger(key, defaultValue)
    }

    override fun getLongPreference(key: String, defaultValue: Long): Preference<Long> {
        return rxPreference.getLong(key, defaultValue)
    }

    override fun getStringPreference(key: String, defaultValue: String): Preference<String> {
        return rxPreference.getString(key, defaultValue)
    }

    override fun getBooleanPreference(key: String, defaultValue: Boolean): Preference<Boolean> {
        return rxPreference.getBoolean(key, defaultValue)
    }

    override fun <T> getPreference(key: String, defaultValue: T): Preference<T> {
        return rxPreference.getObject(key, defaultValue, object : Preference.Converter<T> {
            override fun deserialize(serialized: String): T {

                return gson.fromJson(serialized, object : TypeToken<T>() {}.type)
            }

            override fun serialize(value: T): String {
                return gson.toJson(value)
            }
        })
    }

    override fun clear() {
        preference.edit().clear().apply()
    }
}
