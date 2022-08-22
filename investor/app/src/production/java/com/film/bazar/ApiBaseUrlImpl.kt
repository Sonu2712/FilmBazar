package com.film.bazar

import android.content.SharedPreferences
import com.film.bazar.coredata.ApiBaseUrls

class ApiBaseUrlImpl(val preferences: SharedPreferences) : ApiBaseUrls {
    override fun getMoslApiUrl(): String {
     return ""
    }

    override fun getfallbackSchemeAndHost(): Pair<String, String> {
        return "https" to "maint.film.net"
    }

}
