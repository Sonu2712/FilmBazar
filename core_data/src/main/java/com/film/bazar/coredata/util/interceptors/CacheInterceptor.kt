package com.film.bazar.coredata.util.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import javax.inject.Inject

class CacheInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        return chain.proceed(chain.request())
    }
}
