package com.film.bazar.coredata.util.placeorderapi.placeorderinterceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.RealResponseBody
import okhttp3.internal.http.promisesBody
import okio.GzipSource
import okio.buffer
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncodingPlaceOrderInterceptor @Inject internal constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request().newBuilder()

        original.header("Accept-Encoding", "gzip")

        val response = chain.proceed(original.build())

        if ("gzip".equals(response.header("Content-Encoding"), ignoreCase = true)
            && response.promisesBody()) {
            response.body?.let {
                val responseBuilder = response.newBuilder()
                val gzipSource = GzipSource(it.source())
                val strippedHeaders = response.headers.stripContentEncoding()
                responseBuilder.headers(strippedHeaders)
                val contentType = response.header("Content-Type")
                responseBuilder.body(RealResponseBody(contentType, -1L, gzipSource.buffer()))
//                Timber.d("Buffer Body: $responseBuilder")
                return responseBuilder.build()
            }
        }

        return response
    }

    fun Headers.stripContentEncoding(): Headers {
        return newBuilder()
            .removeAll("Content-Encoding")
            .removeAll("Content-Length")
            .build()
    }
}
