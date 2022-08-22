package com.film.bazar

import android.content.SharedPreferences
import com.film.bazar.coredata.ApiBaseUrls

class ApiBaseUrlImpl(val preferences: SharedPreferences) : ApiBaseUrls {
    override fun getMoslApiUrl(): String {
        //http://14.141.40.4:55333/
        return ""
    }

    override fun getfallbackSchemeAndHost(): Pair<String, String> {
        return "https" to "uatapi.film.net"
    }
}