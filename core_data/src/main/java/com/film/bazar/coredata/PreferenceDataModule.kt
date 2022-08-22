package com.film.bazar.coredata

import android.content.Context
import android.content.SharedPreferences
import com.film.annotations.AppId
import com.film.annotations.FcmToken
import com.film.annotations.FcmTokenRequest
import com.film.annotations.InitTime
import com.film.app.core.prefs.BooleanPreference
import com.film.app.core.prefs.IntegerPreference
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.StringPreference
import com.film.annotations.*
import dagger.Module
import dagger.Provides
import mosl.rx_preferences.Preference
import mosl.rx_preferences.RxSharedPreferences
import com.film.annotations.DelayBetweenOrders
import javax.inject.Singleton

@Module
object PreferenceDataModule {
    const val PREF_FCM_TOKEN = "fcm_token"
    const val PREF_APP_ID = "appid"
    const val PREF_VALUE_PACK = "valuepack"
    const val PREF_APP_SHORTCUT = "appShortcut"
    const val PREF_BRANCH_CLIENT = "branchclient"
    const val PREF_GUEST_SIGN_UP = "guestSignUp"
    const val PREF_PRE_LOGIN = "preLogin"
    const val PREF_PRE_LOGIN_CLIENT_ID = "preLoginClientID"
    const val PREF_UPDATE_TIME = "updatetime_long"
    const val PREF_USER_DELAY_BETWEEN_ORDERS = "delay_between_orders"
    const val SAVED_TOKEN_REQUEST = "saved_token_request"
    const val FROM_FORGOT_PASSWORD = "fromForgotPassword"
    const val APP_MENU = "appMenuPref"
    const val ERROR_LOG = "errorLog"
    const val ERROR_LOG_OLD = "errorLogOld"
    const val ERROR_LOG_SESSION_ID = "errorLogSessionId"

    @Provides
    @Singleton
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun provideRxSharedPreferences(prefs: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(prefs)
    }

    @Singleton
    @Provides
    internal fun provideStreamingToggle(prefs: RxSharedPreferences): Preference<Boolean> {
        return prefs.getBoolean("pref_streaming_toggle", true)
    }

    @AppId
    @Singleton
    @Provides
    internal fun provideAppId(prefs: SharedPreferences): StringPreference {
        return StringPreference(prefs, PREF_APP_ID, "0")
    }

    @ValuePack
    @Singleton
    @Provides
    internal fun provideValuePack(prefs: SharedPreferences): BooleanPreference {
        return BooleanPreference(prefs, PREF_VALUE_PACK, false)
    }

    @AppShortCutId
    @Singleton
    @Provides
    internal fun provideAppShortCutId(prefs: SharedPreferences): StringPreference {
        return StringPreference(prefs, PREF_APP_SHORTCUT, "none")
    }

    @BranchClient
    @Singleton
    @Provides
    internal fun provideBranchClient(prefs: SharedPreferences): BooleanPreference {
        return BooleanPreference(prefs, PREF_BRANCH_CLIENT, false)
    }


    @GuestSignUp
    @Singleton
    @Provides
    internal fun provideGuestSignUp(prefs: SharedPreferences): BooleanPreference {
        return BooleanPreference(prefs, PREF_GUEST_SIGN_UP, false)
    }

    @FromForgotPassword
    @Singleton
    @Provides
    internal fun provideFromForgotPassword(prefs: SharedPreferences): BooleanPreference {
        return BooleanPreference(prefs, FROM_FORGOT_PASSWORD, false)
    }

    @AppMenu
    @Singleton
    @Provides
    internal fun provideAppMenu(prefs: SharedPreferences): StringPreference {
        return StringPreference(prefs, APP_MENU, "")
    }

    @ErrorLog
    @Singleton
    @Provides
    internal fun provideErrorLog(prefs: SharedPreferences) : StringPreference {
        return StringPreference(prefs, ERROR_LOG, "")
    }

    @ErrorLogOld
    @Singleton
    @Provides
    internal fun provideErrorLogOld(prefs: SharedPreferences) : StringPreference {
        return StringPreference(prefs, ERROR_LOG_OLD, "")
    }

    @ErrorLogSessionId
    @Singleton
    @Provides
    internal fun provideErrorLogSessionId(prefs: SharedPreferences) : StringPreference {
        return StringPreference(prefs, ERROR_LOG_SESSION_ID, "")
    }

    @PreLogin
    @Singleton
    @Provides
    internal fun providePreLogin(prefs: SharedPreferences): BooleanPreference {
        return BooleanPreference(prefs, PREF_PRE_LOGIN, false)
    }

    @InitTime
    @Singleton
    @Provides
    internal fun provideInitTime(prefs: SharedPreferences): LongPreference {
        return LongPreference(prefs, PREF_UPDATE_TIME, 0)
    }

    @DelayBetweenOrders
    @Singleton
    @Provides
    fun provideDelayBWOrders(prefs: SharedPreferences): IntegerPreference {
        return IntegerPreference(prefs, PREF_USER_DELAY_BETWEEN_ORDERS, 500)
    }

    @FcmToken
    @Singleton
    @Provides
    fun provideFcmToken(prefs: RxSharedPreferences): Preference<String> {
        return prefs.getString(PREF_FCM_TOKEN, "0")
    }

    @FcmTokenRequest
    @Singleton
    @Provides
    fun provideFcmTokenRequest(prefs: SharedPreferences): StringPreference {
        return StringPreference(prefs, SAVED_TOKEN_REQUEST, "")
    }
}