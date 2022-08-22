package com.film.bazar.coredata

interface ApiBaseUrls : CommonBaseUrls

interface CommonBaseUrls {
    fun getMoslApiUrl(): String
    fun getfallbackSchemeAndHost():Pair<String,String>
}