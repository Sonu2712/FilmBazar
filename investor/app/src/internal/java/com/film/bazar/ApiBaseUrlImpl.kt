package com.film.bazar

import android.content.SharedPreferences
import com.film.bazar.coredata.ApiBaseUrls

class ApiBaseUrlImpl(val preferences: SharedPreferences) : ApiBaseUrls {
    private val defaultChartUrl = "http://chartist.in/mosl/newchartw.html"
    private val defaultAppVideoUrl = "JlHCxeHX0Os"

    override fun getMoslApiUrl(): String {
        /*"UAT Release URL"*/
       // return "https://uatapi.motilaloswal.in/TradingApi/api/"
        /*"UAT Release URL"*/
        //return "https://mobileuath.motilaloswal.com/TradingApi/api/"
        return "https://uatapi.motilaloswal.com/TradingApi/api/"
    }

    override fun getChartUrl(): String {
        return defaultChartUrl
    }

    override fun getOrderUrl(): String {
        return "https://uatapi.motilaloswal.com/PlaceOrderAPi/api/"
    }

    override fun getAppDemoVideoUrl(): String {
        return  defaultAppVideoUrl
    }

  override fun getEkycExpressApiUrl(): String {
    //return "https://ekycuat.motilaloswal.com/express/api/v1/"
    return "https://ekycuat.motilaloswal.com/jet/api/v2/"
  }


    override fun getfallbackSchemeAndHost(): Pair<String, String> {
        return "https" to "uatapi.mofsl.net"
    }
}