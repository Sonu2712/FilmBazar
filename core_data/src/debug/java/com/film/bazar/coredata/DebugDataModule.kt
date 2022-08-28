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
import com.film.app.core.utils.RealClock
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class DebugDataModule {

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
       return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
            Timber.tag("OkHttp").d(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.HEADERS }
    }

    @Provides
    @Singleton
    @Named("BaseOkClient")
    internal fun provideBaseOkHttpClient(
        cacheInterceptor: CacheInterceptor,
        listenerFactory: HttpListenerFactory,
        okHttpCache: Cache,
        pinProvider: CertPinProvider
    ): OkHttpClient {
        return DataModule.createOkHttpClient(pinProvider.getPinner())
            .addNetworkInterceptor(cacheInterceptor)
            .cache(okHttpCache)
            .eventListenerFactory(listenerFactory)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        @Named("BaseOkClient") okHttpClient: OkHttpClient,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @InvestorAPIClient
    internal fun provideInternalAPIOkHttpClient(
        @Named("BaseOkClient") okHttpClient: OkHttpClient,
        headerInterceptor: HeaderInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        encodingInterceptor: EncodingInterceptor,
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
        @Named("BaseOkClient") okHttpClient: OkHttpClient,
        headerInterceptor: HeaderPlaceOrderInterceptor,
        authorizationInterceptor: AuthorizationPlaceOrderInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        encodingInterceptor: EncodingPlaceOrderInterceptor,
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

    @Provides
    internal fun provideRealClock(): RealClock {
        return RealClock
    }
}