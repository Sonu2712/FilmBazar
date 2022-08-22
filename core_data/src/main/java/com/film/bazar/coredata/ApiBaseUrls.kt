package com.film.bazar.coredata

interface ApiBaseUrls : CommonBaseUrls

interface CommonBaseUrls {
    fun getMoslApiUrl(): String
    fun getChartUrl(): String
    fun getOrderUrl(): String
    fun getAppDemoVideoUrl(): String
    fun getEkycExpressApiUrl(): String
    fun getfallbackSchemeAndHost():Pair<String,String>
}