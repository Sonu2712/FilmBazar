package com.film.bazar.coredata

import ProtoResponseUnWrapper
import android.content.Context
import com.google.gson.Gson
import com.film.app.core.rx.DefaultDispatchers
import com.film.app.core.utils.RealClock
import com.film.commons.cache.InMemoryCache
import com.film.bazar.coredata.util.HttpListenerFactory
import dagger.Module
import dagger.Provides
import com.film.annotations.InvestorAPIRetrofit
import com.film.commons.rx.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module(includes = [PreferenceDataModule::class])
object BaseDataModule {

    @Provides
    internal fun provideDispatchers(): Dispatchers {
        return DefaultDispatchers
    }

    @Provides
    @Singleton
    internal fun getRxJava2CallAdapter(dispatchers: Dispatchers): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.createWithScheduler(dispatchers.io)
    }

    @Provides
    @Singleton
    @InvestorAPIRetrofit
    internal fun provideNewRetrofitBuilder(
        client: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder().client(client)
            .addConverterFactory(ProtoResponseUnWrapper())
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(
        client: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gson: Gson
    ): Retrofit
    .Builder {
        return Retrofit.Builder().client(client)
            .addConverterFactory(ProtoResponseUnWrapper())
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
    }

    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        var cache: Cache? = null
        try {
            cache =
                Cache(File(context.cacheDir, "http-cache"), (10 * 1024 * 1024).toLong()) // 10 MB
        } catch (e: Exception) {
            Timber.e(e, "Could not create Cache!")
        }

        return cache ?: throw IllegalStateException("Could not create Cache!")
    }

    @Provides
    internal fun provideEventListenerFactory(): HttpListenerFactory {
        return HttpListenerFactory
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    internal fun provideInMemoryCache(clock: RealClock): InMemoryCache {
        return InMemoryCache(clock)
    }
}
