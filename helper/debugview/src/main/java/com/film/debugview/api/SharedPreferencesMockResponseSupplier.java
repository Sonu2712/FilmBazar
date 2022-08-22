package com.film.debugview.api;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.film.debugview.core.MockResponseSupplier;

public final class SharedPreferencesMockResponseSupplier implements MockResponseSupplier {
    private final SharedPreferences preferences;

    public SharedPreferencesMockResponseSupplier(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override public <T extends Enum<T>> T get(Class<T> cls) {
        String value = preferences.getString(cls.getCanonicalName(), null);
        if (value != null) {
            return Enum.valueOf(cls, value);
        }
        return cls.getEnumConstants()[0];
    }

    @SuppressLint("ApplySharedPref") // Persist to disk because we might kill the process next.
    @Override public void set(Enum<?> value) {
        preferences.edit()
            .putString(value.getDeclaringClass().getCanonicalName(), value.name())
            .commit();
    }
}
