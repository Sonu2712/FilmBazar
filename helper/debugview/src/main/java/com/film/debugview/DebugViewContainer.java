package com.film.debugview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.film.app.core.prefs.BooleanPreference;
import com.film.app.core.base.ViewContainer;
import com.film.debugview.core.DebugDrawerLayout;
import com.film.debugview.core.annotation.PixelGridEnabled;
import com.film.debugview.core.annotation.PixelRatioEnabled;
import com.film.debugview.core.annotation.SeenDebugDrawer;
import com.film.debugview.databinding.DebugActivityFrameBinding;
import com.film.debugview.util.EmptyActivityLifecycleCallbacks;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import mosl.rx_preferences.Preference;

import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

/**
 * An {@link ViewContainer} for debug builds which wraps a sliding drawer on the right that holds
 * all of the debug information and settings.
 */
@Singleton public final class DebugViewContainer implements ViewContainer {
    private final BooleanPreference seenDebugDrawer;
    private final Preference<Boolean> pixelGridEnabled;
    private final Preference<Boolean> pixelRatioEnabled;

    @Inject public DebugViewContainer(@SeenDebugDrawer BooleanPreference seenDebugDrawer,
        @PixelGridEnabled Preference<Boolean> pixelGridEnabled,
        @PixelRatioEnabled Preference<Boolean> pixelRatioEnabled) {
        this.seenDebugDrawer = seenDebugDrawer;
        this.pixelGridEnabled = pixelGridEnabled;
        this.pixelRatioEnabled = pixelRatioEnabled;
    }

    /**
     * Show the activity over the lock-screen and wake up the device. If you launched the app
     * manually
     * both of these conditions are already true. If you deployed from the IDE, however, this will
     * save you from hundreds of power button presses and pattern swiping per day!
     */
    public static void riseAndShine(Activity activity) {
        activity.getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);

        PowerManager power = (PowerManager) activity.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock lock =
            power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE,
                "mosl:wakeup!");
        lock.acquire();
        lock.release();
    }

    @Override public ViewGroup forActivity(@NonNull final Activity activity) {
        DebugActivityFrameBinding binding =
            DebugActivityFrameBinding.inflate(activity.getLayoutInflater());
        activity.setContentView(binding.getRoot());

        final Context drawerContext = new ContextThemeWrapper(activity, R.style.Theme_U2020_Debug);
        final DebugView debugView = new DebugView(drawerContext);
        binding.debugDrawer.addView(debugView);

        binding.debugDrawerLayout.setDrawerShadow(R.drawable.debug_drawer_shadow,
            GravityCompat.END);
        binding.debugDrawerLayout.setDrawerListener(new DebugDrawerLayout.SimpleDrawerListener() {
            @Override public void onDrawerOpened(View drawerView) {
                debugView.onDrawerOpened();
            }
        });

        // If you have not seen the debug drawer before, show it with a message
        if (!seenDebugDrawer.get()) {
            binding.debugDrawerLayout.postDelayed(() -> {
                binding.debugDrawerLayout.openDrawer(GravityCompat.END);
                Toast.makeText(drawerContext, R.string.debug_drawer_welcome, Toast.LENGTH_LONG)
                    .show();
            }, 1000);
            seenDebugDrawer.set(true);
        }

        final CompositeDisposable subscriptions = new CompositeDisposable();
        setupMadge(binding, subscriptions);

        final Application app = activity.getApplication();
        app.registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks() {
            @Override public void onActivityDestroyed(Activity lifecycleActivity) {
                if (lifecycleActivity == activity) {
                    subscriptions.dispose();
                    app.unregisterActivityLifecycleCallbacks(this);
                }
            }
        });

        riseAndShine(activity);
        return binding.debugContent;
    }

    private void setupMadge(final DebugActivityFrameBinding binding,
        CompositeDisposable subscriptions) {
        subscriptions.add(pixelGridEnabled.asObservable().subscribe(
            binding.madgeContainer::setOverlayEnabled));
        subscriptions.add(pixelRatioEnabled.asObservable().subscribe(
            binding.madgeContainer::setOverlayRatioEnabled));
    }
}
