package com.film.bazar.appuser.module

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.film.annotations.*
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.StringPreference
import com.film.app.core.utils.RealClock
import com.film.bazar.appuser.helper.LoginPrefStore
import com.film.bazar.appuser.helper.getPref
import com.film.bazar.appuser.meta.SafePreference
import com.film.bazar.appuser.meta.UserPreference
import com.film.bazar.appuser.repository.*
import com.film.commons.utils.Clock
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import mosl.rx_preferences.Preference
import javax.inject.Singleton

@Module
abstract class UserModule {
    @Binds
    abstract fun provideUserManager(userManager: UserManagerImpl): UserManager

    companion object {
        const val USER_MANAGER_PREF = ".user_manager"
        const val SAFE_USER_MANAGER__PREF = ".safe_user_manager"
        const val PREF_SERVER_TIME = "pref_server_time_millis"

        @Provides
        @UserPreference
        @Singleton
        fun provideUserCredPreference(context: Context): SharedPreferences {
            return context.getPref(context.packageName + USER_MANAGER_PREF)
        }

        @Provides
        @SafePreference
        @Singleton
        fun provideSafePreference(context: Context): SharedPreferences {
            val masterKeyAlias = MasterKey.Builder(
                context, "MASTER_KEY_ALIAS")
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                context,
                context.packageName + SAFE_USER_MANAGER__PREF,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
        }

        @Provides
        fun provideLoginPrefStore(userDataStore: UserDataStore): LoginPrefStore {
            return userDataStore.loginPrefStore()
        }

        @ClientCode
        @Singleton
        @Provides
        internal fun provideClientCodePref(loginPrefStore: LoginPrefStore): StringPreference {
            return loginPrefStore.clientCode
        }

        @RxClientCode
        @Provides
        internal fun provideRxClientCode(userDataStore: UserDataStore): Preference<String> {
            return userDataStore.rxClientCode()
        }

        @Provides
        @Singleton
        internal fun provideUserCache(
            @UserPreference preferences: SharedPreferences,
            @SafePreference safePreference: SharedPreferences,
            gson: Gson
        ): UserCache {
            val userDataStore =
                UserDataStoreImpl(
                    preferences,
                    safePreference,
                    gson
                )
            return UserCacheImpl(
                userDataStore,
                gson
            )
        }

        @Provides
        fun provideClock(@ServerTime serverTime: LongPreference): Clock {
            return RealClock
        }

        @UserIdentity
        @Provides
        fun provideUserIdentity(loginPrefStore: LoginPrefStore): StringPreference {
            return loginPrefStore.userIdentity
        }

        @Provides
        fun provideUserDataStore(userCache: UserCache): UserDataStore {
            return userCache.dataStore()
        }

        @ServerTime
        @Singleton
        @Provides
        fun provideServerTimePref(prefs: SharedPreferences?): LongPreference {
            return LongPreference(prefs!!, PREF_SERVER_TIME, 0)
        }

        /*@Provides
        @Singleton
        fun provideTokenPrefStore(
            @UserPreference preferences: SharedPreferences,
            @SafePreference safePreference: SharedPreferences
        ): TokenPrefStore {
            return createTokenPrefStore(preferences, safePreference)
        }*/

        @DateOfBirth
        @Provides
        fun provideDateOfBirth(loginPrefStore: LoginPrefStore): StringPreference {
            return loginPrefStore.multiFactorInfo
        }
    }
}
