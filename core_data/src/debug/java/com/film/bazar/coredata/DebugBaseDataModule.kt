package com.film.bazar.coredata

import ProtoResponseUnWrapper
import com.google.gson.Gson
import com.film.bazar.coredata.converterfactory.ProtoJsonConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DebugBaseDataModule {
    @Provides
    @Singleton
    @Named("BaseInternalAPIRetrofit")
    internal fun provideDebugNewRetrofitBuilder(
        @Named("BaseOkClient") client: OkHttpClient,
        gson: Gson,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder().client(client)
            .addConverterFactory(ProtoJsonConverterFactory())
            .addConverterFactory(ProtoResponseUnWrapper())
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
    }

    @Provides
    @Singleton
    @Named("BaseAPIRetrofit")
    internal fun provideDebugRetrofitBuilder(
        @Named("BaseOkClient") client: OkHttpClient,
        gson: Gson,
       rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit
    .Builder {
        return Retrofit.Builder().client(client)
            .addConverterFactory(ProtoJsonConverterFactory())
            .addConverterFactory(ProtoResponseUnWrapper())
            .addConverterFactory(ProtoConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
    }
}
