package com.film.bazar

import android.content.SharedPreferences
import com.film.bazar.coredata.ApiBaseUrls

class ApiBaseUrlImpl(val preferences: SharedPreferences) : ApiBaseUrls {
    private val defaultChartUrl = "https://tfc.chartist.in/mosl_mob/"
    private val defaultAppVideoUrl = "JlHCxeHX0Os"
    override fun getMoslApiUrl(): String {
        // For Pilot release
      //  return "https://uatapi.motilaloswal.com/TradingApiProd/api/"
        //For Production release
     return "https://maint.motilaloswal.com/TradingApi/api/"
        //For DR release
        //return "https://my.motilaloswal.com/TradingApi/api/"
    }

    override fun getChartUrl(): String {
        return preferences.getString("", defaultChartUrl) ?: defaultChartUrl
    }

    override fun getAppDemoVideoUrl(): String {
        return preferences.getString("", defaultAppVideoUrl)
            ?: defaultAppVideoUrl
    }

    override fun getOrderUrl(): String {
        return "https://uatapi.motilaloswal.com/PlaceOrderAPi/api/"
    }

    override fun getEkycExpressApiUrl(): String {
        //return "https://www.motilaloswal.com/open-demat-account/api/v1/"
        return "https://ekyc.motilaloswal.com/open-demat-account/api/v2/"
    }

    override fun getfallbackSchemeAndHost(): Pair<String, String> {
        //Need confirmation from Ujjawal side to set Https
        // changed to primary production url
        return "https" to "maint.mofsl.net"
    }

}
