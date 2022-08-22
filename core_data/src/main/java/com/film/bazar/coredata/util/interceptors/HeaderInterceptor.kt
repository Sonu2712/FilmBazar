package com.film.bazar.coredata.util.interceptors

import com.film.annotations.ServerTime
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.StringPreference
import com.film.commons.appinfo.AppInfo
import com.film.annotations.AppId
import com.film.annotations.UserLanguage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    @param:AppId private val appId: StringPreference,
    private val appInfo: AppInfo,
    @param:ServerTime private val serverTimePref: LongPreference,
    @param:UserLanguage private val userLanguage: StringPreference
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val userAgent = "$appInfo/${appId.get()}"

        val builder = original.newBuilder().apply {
            header(API_HEADER_USER_AGENT, userAgent)
            header(API_HEADER_ACCEPT, API_HEADER_ACCEPT_PROTOBUF)
            header(API_HEADER_REQUEST_ID, UUID.randomUUID().toString())
            if (userLanguage.get() != "en") {
                header(API_HEADER_USER_LANGUAGE, userLanguage.get())
            }
        }

        val response = chain.proceed(builder.build())

        val date = response.headers
            .getDate(API_HEADER_DATE)
        if (date != null) {
            serverTimePref.set(date.time)
        }

        return response
    }

    companion object {
        private const val API_HEADER_ACCEPT = "Accept"
        private const val API_HEADER_ACCEPT_PROTOBUF = "application/x-protobuf"
        private const val API_HEADER_USER_AGENT = "User-Agent"
        private const val API_HEADER_REQUEST_ID = "Request-Id"
        private const val API_HEADER_USER_LANGUAGE = "Accept-Language"
        private const val API_HEADER_DATE = "Date"
        private const val API_HEADER_OKHTTP_VERSION = "Okhttp-Version"
        internal const val DateHeader = "x-mofsl-date"

        val dateFormat = SimpleDateFormat("yyyyMMdd'T'HHMMSS'Z'", Locale.ENGLISH)
        fun GetRequestDate() : String {
            return dateFormat.format(Date())
        }
    }
}