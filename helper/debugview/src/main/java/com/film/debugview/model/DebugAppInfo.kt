package com.film.debugview.model

data class DebugAppInfo(
    @JvmField val appName: String,
    @JvmField val versionName: String,
    @JvmField val versionCode: Int,
    @JvmField val gitSha: String,
    @JvmField val gitTimeStamp: Long,
    @JvmField val launcherActivityName: Class<*>,
    @JvmField val dashboardActivityName: Class<*>,
    @JvmField val dashboardPageId: Int
)