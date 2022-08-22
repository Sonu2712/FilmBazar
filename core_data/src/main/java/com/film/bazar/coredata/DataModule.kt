package com.film.bazar.coredata

import com.film.bazar.coredata.util.CertPinProvider
import com.film.bazar.coredata.util.HttpListenerFactory
import com.film.bazar.coredata.util.interceptors.*
import com.film.bazar.coredata.util.placeorderapi.encryptor.EncryptionInterceptor
import com.film.bazar.coredata.util.placeorderapi.placeorderinterceptor.AuthorizationPlaceOrderInterceptor
import com.film.bazar.coredata.util.placeorderapi.placeorderinterceptor.EncodingPlaceOrderInterceptor
import com.film.bazar.coredata.util.placeorderapi.placeorderinterceptor.HeaderPlaceOrderInterceptor
import com.film.bazar.coredata.util.placeorderapi.sign.SigningInterceptor
import dagger.Module
import dagger.Provides
import com.film.annotations.InvestorAPIClient
import com.film.annotations.PlaceOrderAPIClient
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object DataModule {
    fun createOkHttpClient(pinner: CertificatePinner?): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
        if (pinner != null) {
            builder.certificatePinner(pinner)
        }
        return builder
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        cacheInterceptor: CacheInterceptor,
        listenerFactory: HttpListenerFactory,
        okHttpCache: Cache,
        pinProvider: CertPinProvider
    ): OkHttpClient {
        return createOkHttpClient(pinProvider.getPinner())
            .addNetworkInterceptor(cacheInterceptor)
            .cache(okHttpCache)
            .eventListenerFactory(listenerFactory)
            .build()
    }

    @Provides
    @Singleton
    @InvestorAPIClient
    internal fun provideInternalAPIOkHttpClient(
        okHttpClient: OkHttpClient,
        headerInterceptor: HeaderInterceptor,
        loggingInterceptor: LoggingInterceptor,
        encodingInterceptor: EncodingInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(encodingInterceptor)
            .addInterceptor(hostSelectionInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @PlaceOrderAPIClient
    internal fun providePlaceOrderAPIClient(
        okHttpClient: OkHttpClient,
        headerInterceptor: HeaderPlaceOrderInterceptor,
        loggingInterceptor: LoggingInterceptor,
        encodingInterceptor: EncodingPlaceOrderInterceptor,
        authorizationInterceptor: AuthorizationPlaceOrderInterceptor,
        encryptionInterceptor: EncryptionInterceptor,
        signingInterceptor: SigningInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(encodingInterceptor)
            .addInterceptor(encryptionInterceptor)
            .addInterceptor(signingInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}