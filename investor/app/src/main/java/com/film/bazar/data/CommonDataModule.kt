package com.film.bazar.data

import android.content.Context
import android.content.SharedPreferences
import com.film.bazar.BuildConfig
import com.film.bazar.R
import com.film.app.core.utils.DeviceStatistics.osVersionNumber
import com.film.commons.appinfo.AppInfo
import com.film.login.data.provider.LoginDataModule
import com.film.bazar.ApiBaseUrlImpl
import com.film.bazar.CertPinProviderImpl
import com.film.bazar.appuser.UserChangeEvent
import com.film.bazar.appuser.module.UserModule
import com.film.bazar.coredata.BaseDataModule
import com.film.bazar.coredata.CommonBaseUrls
import com.film.bazar.coredata.util.CertPinProvider
import com.film.bazar.data.util.DefaultUserChangeEvent
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [BaseDataModule::class, RepositoryAliasProvider::class,
        LoginDataModule::class, UserModule::class]
)
abstract class CommonDataModule {
    @Binds
    abstract fun provideUserChangeEvent(userChangeEvent: DefaultUserChangeEvent): UserChangeEvent

    companion object {
        @Provides
        @Singleton
        internal fun provideApiBaseUrl(preferences: SharedPreferences): CommonBaseUrls {
            return ApiBaseUrlImpl(preferences)
        }

        @Provides
        internal fun provideCertPinnerProvider(): CertPinProvider {
            return CertPinProviderImpl
        }

        @Provides
        fun provideAppInfo(context: Context): AppInfo {
            return AppInfo(
                name = context.getString(R.string.main_app_name),
                os = "Android",
                osVersion = osVersionNumber,
                versionName = BuildConfig.VERSION_NAME,
                versionCode = BuildConfig.VERSION_CODE,
                sha = "BuildConfig.GIT_SHA",
                appMarket = "androidmarket",
                source = BuildConfig.APP_SOURCE
            )
        }
    }
}