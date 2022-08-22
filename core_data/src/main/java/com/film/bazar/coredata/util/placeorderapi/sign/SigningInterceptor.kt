package com.film.bazar.coredata.util.placeorderapi.sign

import com.film.app.core.prefs.StringPreference
import com.film.annotations.AppId
import com.film.annotations.ClientCode
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SigningInterceptor @Inject constructor(
    @param:AppId private val appId: StringPreference,
    @param:ClientCode protected val clientCode: StringPreference) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val signingHeader = request.getSigningHeader(clientCode.get(), appId.get())
        val signedRequest = request
            .newBuilder()
            .addHeader(AllHeaders.SigningKeyHeader, signingHeader)
            .build()

        return chain.proceed(signedRequest)
    }
}