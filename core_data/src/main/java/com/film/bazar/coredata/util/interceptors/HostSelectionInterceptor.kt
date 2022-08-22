package com.film.bazar.coredata.util.interceptors

import android.content.SharedPreferences
import com.film.bazar.coredata.CommonBaseUrls
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HostSelectionInterceptor @Inject constructor(
    val preferences: SharedPreferences,
    val apiBaseUrls: CommonBaseUrls
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var fallbackUrl: HttpUrl =
            request.url.newBuilder().scheme(apiBaseUrls.getfallbackSchemeAndHost().first)
                .host(apiBaseUrls.getfallbackSchemeAndHost().second).build()
        if (!preferences.getBoolean(PREF_IS_PRIMARY_BASE_URL_WORKING, true)) {
            request = request.newBuilder()
                .url(fallbackUrl)
                .build()

        }
        return chain.proceed(request)
    }

    companion object {
        const val PREF_IS_PRIMARY_BASE_URL_WORKING = "isPrimaryBaseUrlWorking"
        const val PREF_IS_SECONDARY_BASE_URL_WORKING = "isSecondaryBaseUrlWorking"
    }
}