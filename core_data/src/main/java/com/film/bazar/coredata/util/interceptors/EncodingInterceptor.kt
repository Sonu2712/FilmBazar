package com.film.bazar.coredata.util.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncodingInterceptor @Inject internal constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        return chain.proceed(chain.request())
    }
}
