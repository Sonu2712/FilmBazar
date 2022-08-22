package com.film.bazar.coredata.util.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Timber.e("Request Failed: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        Timber.i("Request Info : ${request.method} ${request.url} (${tookMs}ms)")

        return response
    }
}
