package com.film.debugview;

import com.film.debugview.core.NetworkErrorCode;
import com.film.debugview.core.annotation.NetworkDelay;
import com.film.debugview.core.annotation.NetworkErrorPercent;
import com.film.debugview.core.annotation.NetworkFailurePercent;
import com.film.debugview.core.annotation.NetworkVariancePercent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mosl.rx_preferences.Preference;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Module public abstract class MockApiModule {
    @Provides @Singleton
    static NetworkBehavior provideBehavior(@NetworkDelay Preference<Long> networkDelay,
        @NetworkVariancePercent Preference<Integer> networkVariancePercent,
        @NetworkFailurePercent Preference<Integer> networkFailurePercent,
        @NetworkErrorPercent Preference<Integer> networkErrorPercent,
        Preference<NetworkErrorCode> networkErrorCode) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(networkDelay.get(), MILLISECONDS);
        behavior.setVariancePercent(networkVariancePercent.get());
        behavior.setFailurePercent(networkFailurePercent.get());
        behavior.setErrorPercent(networkErrorPercent.get());
        behavior.setErrorFactory(() -> Response.error(networkErrorCode.get().code,
            ResponseBody.create(null, new byte[0])));
        return behavior;
    }

    @Provides @Singleton
    static MockRetrofit provideMockRetrofit(Retrofit.Builder builder, NetworkBehavior behavior) {
        return new MockRetrofit.Builder(
            builder.baseUrl("http://mock.retrofit.api/").build()).networkBehavior(behavior).build();
    }
}
