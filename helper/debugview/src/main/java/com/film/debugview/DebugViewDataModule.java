package com.film.debugview;

import android.content.SharedPreferences;

import com.film.app.core.prefs.BooleanPreference;
import com.film.debugview.api.SharedPreferencesMockResponseSupplier;
import com.film.debugview.core.MockResponseSupplier;
import com.film.debugview.core.NetworkErrorCode;
import com.film.debugview.core.annotation.AnimationSpeed;
import com.film.debugview.core.annotation.ApiEndpoint;
import com.film.debugview.core.annotation.CaptureIntents;
import com.film.debugview.core.annotation.IsMockMode;
import com.film.debugview.core.annotation.NetworkDelay;
import com.film.debugview.core.annotation.NetworkErrorPercent;
import com.film.debugview.core.annotation.NetworkFailurePercent;
import com.film.debugview.core.annotation.NetworkVariancePercent;
import com.film.debugview.core.annotation.PixelGridEnabled;
import com.film.debugview.core.annotation.PixelRatioEnabled;
import com.film.debugview.core.annotation.SeenDebugDrawer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mosl.rx_preferences.Preference;
import mosl.rx_preferences.RxSharedPreferences;

@Module public abstract class DebugViewDataModule {
    private static final int DEFAULT_ANIMATION_SPEED = 1; // 1x (normal) speed.
    private static final boolean DEFAULT_SEEN_DEBUG_DRAWER = false; // Show debug drawer first time.
    private static final boolean DEFAULT_PIXEL_GRID_ENABLED = false; // No pixel grid overlay.
    private static final boolean DEFAULT_PIXEL_RATIO_ENABLED = false; // No pixel ratio overlay.
    private static final boolean DEFAULT_CAPTURE_INTENTS = false;
    private static final boolean DEFAULT_ACCEPT_JSON = false;

    @Provides @Singleton @SeenDebugDrawer
    static BooleanPreference provideSeenDebugDrawer(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_seen_debug_drawer",
            DEFAULT_SEEN_DEBUG_DRAWER);
    }

    @Provides @Singleton @CaptureIntents
    static Preference<Boolean> provideCaptureIntentsPreference(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_capture_intents", DEFAULT_CAPTURE_INTENTS);
    }

    @Provides @Singleton @AnimationSpeed
    static Preference<Integer> provideAnimationSpeed(RxSharedPreferences preferences) {
        return preferences.getInteger("debug_animation_speed", DEFAULT_ANIMATION_SPEED);
    }

    @Provides @Singleton @PixelGridEnabled
    static Preference<Boolean> providePixelGridEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_pixel_grid_enabled", DEFAULT_PIXEL_GRID_ENABLED);
    }

    @Provides @Singleton @PixelRatioEnabled
    static Preference<Boolean> providePixelRatioEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_pixel_ratio_enabled", DEFAULT_PIXEL_RATIO_ENABLED);
    }

    @Provides @Singleton @IsMockMode
    static boolean provideIsMockMode(@ApiEndpoint Preference<String> endpoint) {
        // Running in an instrumentation forces mock mode.
        return ApiEndpoints.isMockMode(endpoint.get());
    }

    @Provides @Singleton @ApiEndpoint
    static Preference<String> provideEndpointPreference(RxSharedPreferences preferences) {
        return preferences.getString("debug_endpoint", ApiEndpoints.UAT.name);
    }

    @Provides @Singleton @NetworkDelay
    static Preference<Long> provideNetworkDelay(RxSharedPreferences preferences) {
        return preferences.getLong("debug_network_delay", 2000L);
    }

    @Provides @Singleton @NetworkVariancePercent
    static Preference<Integer> provideNetworkVariancePercent(RxSharedPreferences preferences) {
        return preferences.getInteger("debug_network_variance_percent", 40);
    }

    @Provides @Singleton @NetworkFailurePercent
    static Preference<Integer> provideNetworkFailurePercent(RxSharedPreferences preferences) {
        return preferences.getInteger("debug_network_failure_percent", 3);
    }

    @Provides @Singleton @NetworkErrorPercent
    static Preference<Integer> provideNetworkErrorPercent(RxSharedPreferences preferences) {
        return preferences.getInteger("debug_network_error_percent", 0);
    }

    @Provides @Singleton
    static Preference<NetworkErrorCode> provideNetworkErrorCode(RxSharedPreferences preferences) {
        return preferences.getEnum("debug_network_error_code", NetworkErrorCode.HTTP_500,
            NetworkErrorCode.class);
    }

    @Provides @Singleton
    static MockResponseSupplier provideResponseSupplier(SharedPreferences preferences) {
        return new SharedPreferencesMockResponseSupplier(preferences);
    }
}
