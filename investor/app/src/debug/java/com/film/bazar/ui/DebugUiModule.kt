package com.film.bazar.ui

import android.content.Context
import com.film.bazar.BuildConfig
import com.film.bazar.R
import com.film.debugview.model.DebugAppInfo
import com.film.bazar.core.MOSLCommonActivity
import com.film.bazar.splash.SplashActivity
import dagger.Module
import dagger.Provides


@Module
object DebugUiModule {
    @Provides
    internal fun provideDebugAppInfo(context: Context): DebugAppInfo {
        return DebugAppInfo(
            context.getString(R.string.main_app_name),
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE,
            "",
            6789,
            SplashActivity::class.java,
            MOSLCommonActivity::class.java,
            -100
        )
    }
}