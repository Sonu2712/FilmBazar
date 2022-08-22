package com.film.debugview;

import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.jakewharton.processphoenix.ProcessPhoenix;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxAdapterView;
import com.film.app.core.utils.Injector;
import com.film.debugview.core.AnimationSpeedAdapter;
import com.film.debugview.core.NetworkErrorCode;
import com.film.debugview.core.adapter.EnumAdapter;
import com.film.debugview.core.adapter.NetworkDelayAdapter;
import com.film.debugview.core.adapter.NetworkPercentageAdapter;
import com.film.debugview.core.adapter.NetworkVarianceAdapter;
import com.film.debugview.core.annotation.AnimationSpeed;
import com.film.debugview.core.annotation.ApiEndpoint;
import com.film.debugview.core.annotation.CaptureIntents;
import com.film.debugview.core.annotation.NetworkDelay;
import com.film.debugview.core.annotation.NetworkErrorPercent;
import com.film.debugview.core.annotation.NetworkFailurePercent;
import com.film.debugview.core.annotation.NetworkVariancePercent;
import com.film.debugview.core.annotation.PixelGridEnabled;
import com.film.debugview.core.annotation.PixelRatioEnabled;
import com.film.debugview.databinding.DebugViewContentBinding;
import com.film.debugview.logs.LogsDialog;
import com.film.debugview.model.DebugAppInfo;
import com.film.debugview.util.StringUtils;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import mosl.rx_preferences.Preference;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.mock.NetworkBehavior;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public final class DebugView extends FrameLayout {

    private static final DateTimeFormatter DATE_DISPLAY_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.US)
            .withZone(ZoneId.systemDefault());

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject @ApiEndpoint
    Preference<String> networkEndpoint;
    @Inject NetworkBehavior behavior;
    @Inject @NetworkDelay
    Preference<Long> networkDelay;
    @Inject @NetworkVariancePercent Preference<Integer> networkVariancePercent;
    @Inject @NetworkFailurePercent Preference<Integer> networkFailurePercent;
    @Inject @NetworkErrorPercent Preference<Integer> networkErrorPercent;
    @Inject Preference<NetworkErrorCode> networkErrorCode;

    @Inject @CaptureIntents Preference<Boolean> captureIntents;

    @Inject @AnimationSpeed Preference<Integer> animationSpeed;

    @Inject OkHttpClient client;
    @Inject DebugAppInfo appInfo;
    @Inject LumberYard lumberYard;
    @Inject Application app;
    @Inject @PixelGridEnabled Preference<Boolean> pixelGridEnabled;
    @Inject @PixelRatioEnabled Preference<Boolean> pixelRatioEnabled;

    DebugViewContentBinding binding;

    public DebugView(Context context) {
        this(context, null);
    }

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Injector.obtain(context.getApplicationContext(), DebugComponent.class).inject(this);

        // Inflate all of the controls and inject them.
        binding = DebugViewContentBinding.inflate(LayoutInflater.from(context), this, true);
        setupNetworkSection();
        setupCaptureIntentSection();
        setupUserInterfaceSection();
        setupBuildSection();
        setupDeviceSection();
        setupOkHttpCacheSection();

        binding.txtAppName.setText(appInfo.appName);

        disposable.add(RxView.clicks(binding.debugLogsShow).subscribe(ignored -> showLogs()));
        disposable.add(
            RxView.clicks(binding.debugDashboardPage).subscribe(ignored -> showDashboard()));
        disposable.add(RxView.clicks(binding.debugLeaksShow).subscribe(ignored -> showLeaks()));
        disposable.add(
            RxView.clicks(binding.debugOkhttpClearCache).subscribe(ignored -> clearCache()));
    }

    private static String getDensityString(DisplayMetrics displayMetrics) {
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi";
            case DisplayMetrics.DENSITY_TV:
                return "tvdpi";
            default:
                return String.valueOf(displayMetrics.densityDpi);
        }
    }

    private static String getSizeString(long bytes) {
        String[] units = new String[] { "B", "KB", "MB", "GB" };
        int unit = 0;
        while (bytes >= 1024) {
            bytes /= 1024;
            unit += 1;
        }
        return bytes + units[unit];
    }

    @Override protected void onDetachedFromWindow() {
        disposable.clear();
        super.onDetachedFromWindow();
    }

    public void onDrawerOpened() {
        Cache cache = client.cache();
        if (cache != null) {
            refreshOkHttpCacheStats(cache);
        }
    }

    void showLogs() {
        new LogsDialog(new ContextThemeWrapper(getContext(), R.style.Theme_U2020_External),
            lumberYard).show();
    }

    void showLeaks() {
//        Intent intent = new Intent(getContext(),LeakActivity.class);
//        getContext().startActivity;
    }

    void showDashboard() {
        Context context = getContext();
        Intent intent =
            new Intent(context, appInfo.dashboardActivityName);
        intent.putExtra("pageId", appInfo.dashboardPageId);
        context.startActivity(intent);
    }

    private void setupNetworkSection() {
        final ApiEndpoints currentEndpoint = ApiEndpoints.from(networkEndpoint.get());
        final EnumAdapter<ApiEndpoints> endpointAdapter =
            new EnumAdapter<>(getContext(), ApiEndpoints.class);
        Spinner endpointView = binding.debugNetworkEndpoint;
        endpointView.setAdapter(endpointAdapter);
        endpointView.setSelection(currentEndpoint.ordinal());

        Observable<ApiEndpoints> apiEndpointsObservable = RxAdapterView.itemSelections(endpointView)
            .map(endpointAdapter::getItem)
            .filter(item -> item != currentEndpoint)
            .share();

        disposable.add(apiEndpointsObservable.subscribe(selected -> {
            Timber.d("Setting network endpoint to %s", selected.name);
            networkEndpoint.set(selected.name);
        }));

        disposable.add(apiEndpointsObservable.delay(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(selected -> relaunch()));

        Spinner networkDelayView = binding.debugNetworkDelay;
        final NetworkDelayAdapter delayAdapter = new NetworkDelayAdapter(getContext());
        networkDelayView.setAdapter(delayAdapter);
        networkDelayView.setSelection(
            NetworkDelayAdapter.getPositionForValue(behavior.delay(MILLISECONDS)));

        disposable.add(RxAdapterView.itemSelections(networkDelayView)
            .map(delayAdapter::getItem)
            .filter(item -> item != behavior.delay(MILLISECONDS))
            .subscribe(selected -> {
                Timber.d("Setting network delay to %sms", selected);
                behavior.setDelay(selected, MILLISECONDS);
                networkDelay.set(selected);
            }));

        Spinner networkVarianceView = binding.debugNetworkVariance;
        final NetworkVarianceAdapter varianceAdapter = new NetworkVarianceAdapter(getContext());
        networkVarianceView.setAdapter(varianceAdapter);
        networkVarianceView.setSelection(
            NetworkVarianceAdapter.getPositionForValue(behavior.variancePercent()));

        disposable.add(RxAdapterView.itemSelections(networkVarianceView)
            .map(varianceAdapter::getItem)
            .filter(item -> item != behavior.variancePercent())
            .subscribe(selected -> {
                Timber.d("Setting network variance to %s%%", selected);
                behavior.setVariancePercent(selected);
                networkVariancePercent.set(selected);
            }));

        Spinner networkFailureView = binding.debugNetworkFailure;
        final NetworkPercentageAdapter failureAdapter = new NetworkPercentageAdapter(getContext());
        networkFailureView.setAdapter(failureAdapter);
        networkFailureView.setSelection(
            NetworkPercentageAdapter.getPositionForValue(behavior.failurePercent()));

        disposable.add(RxAdapterView.itemSelections(networkFailureView)
            .map(failureAdapter::getItem)
            .filter(item -> item != behavior.failurePercent())
            .subscribe(selected -> {
                Timber.d("Setting network failure to %s%%", selected);
                behavior.setFailurePercent(selected);
                networkFailurePercent.set(selected);
            }));

        Spinner networkErrorView = binding.debugNetworkError;
        final NetworkPercentageAdapter errorAdapter = new NetworkPercentageAdapter(getContext());
        networkErrorView.setAdapter(errorAdapter);
        networkErrorView.setSelection(
            NetworkPercentageAdapter.getPositionForValue(behavior.errorPercent()));

        disposable.add(RxAdapterView.itemSelections(networkErrorView)
            .map(errorAdapter::getItem)
            .filter(item -> item != behavior.errorPercent())
            .subscribe(selected -> {
                Timber.d("Setting network error to %s%%", selected);
                behavior.setErrorPercent(selected);
                networkErrorPercent.set(selected);
            }));

        Spinner networkErrorCodeView = binding.debugNetworkErrorCode;
        final EnumAdapter<NetworkErrorCode> errorCodeAdapter =
            new EnumAdapter<>(getContext(), NetworkErrorCode.class);
        networkErrorCodeView.setAdapter(errorCodeAdapter);
        networkErrorCodeView.setSelection(networkErrorCode.get().ordinal());

        disposable.add(RxAdapterView.itemSelections(networkErrorCodeView)
            .map(errorCodeAdapter::getItem)
            .filter(item -> item != networkErrorCode.get())
            .subscribe(selected -> {
                Timber.d("Setting network error code to %s%%", selected);
                networkErrorCode.set(selected);
                // No need to update 'behavior' as the factory already pulls from the preference.
            }));
    }

    private void setupCaptureIntentSection() {
        Switch captureIntentsView = binding.debugCaptureIntents;
        captureIntentsView.setChecked(captureIntents.get());
        captureIntentsView.setOnCheckedChangeListener((compoundButton, b) -> {
            Timber.d("Capture intents set to %s", b);
            captureIntents.set(b);
        });
    }

    private void setupUserInterfaceSection() {
        Spinner uiAnimationSpeedView = binding.debugUiAnimationSpeed;
        final AnimationSpeedAdapter speedAdapter = new AnimationSpeedAdapter(getContext());
        uiAnimationSpeedView.setAdapter(speedAdapter);
        final int animationSpeedValue = animationSpeed.get();
        uiAnimationSpeedView.setSelection(
            AnimationSpeedAdapter.getPositionForValue(animationSpeedValue));

        disposable.add(RxAdapterView.itemSelections(uiAnimationSpeedView)
            .map(speedAdapter::getItem)
            .filter(item -> !item.equals(animationSpeed.get()))
            .subscribe(selected -> {
                Timber.d("Setting animation speed to %sx", selected);
                animationSpeed.set(selected);
                applyAnimationSpeed(selected);
            }));
        // Ensure the animation speed value is always applied across app restarts.
        post(() -> applyAnimationSpeed(animationSpeedValue));

        Switch uiPixelGridView = binding.debugUiPixelGrid;
        Switch uiPixelRatioView = binding.debugUiPixelRatio;

        boolean gridEnabled = pixelGridEnabled.get();
        uiPixelGridView.setChecked(gridEnabled);
        uiPixelRatioView.setEnabled(gridEnabled);
        uiPixelGridView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Timber.d("Setting pixel grid overlay enabled to %b", isChecked);
            pixelGridEnabled.set(isChecked);
            uiPixelRatioView.setEnabled(isChecked);
        });

        uiPixelRatioView.setChecked(pixelRatioEnabled.get());
        uiPixelRatioView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Timber.d("Setting pixel scale overlay enabled to %b", isChecked);
            pixelRatioEnabled.set(isChecked);
        });
    }

    private void setupBuildSection() {
        binding.debugBuildName.setText(appInfo.appName);
        binding.debugBuildCode.setText(String.valueOf(appInfo.versionCode));
        binding.debugBuildSha.setText(appInfo.gitSha);

        TemporalAccessor buildTime = Instant.ofEpochSecond(appInfo.gitTimeStamp);
        binding.debugBuildDate.setText(DATE_DISPLAY_FORMAT.format(buildTime));
    }

    private void setupDeviceSection() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        String densityBucket = getDensityString(displayMetrics);
        binding.debugDeviceMake.setText(StringUtils.truncateAt(Build.MANUFACTURER, 20));
        binding.debugDeviceModel.setText(StringUtils.truncateAt(Build.MODEL, 20));
        binding.debugDeviceResolution.setText(
            String.format(Locale.ENGLISH, "%dx%d", displayMetrics.heightPixels,
                displayMetrics.widthPixels));
        binding.debugDeviceDensity.setText(
            String.format(Locale.ENGLISH, "%ddpi (%s)", displayMetrics.densityDpi, densityBucket));
        binding.debugDeviceRelease.setText(Build.VERSION.RELEASE);
        binding.debugDeviceApi.setText(String.valueOf(Build.VERSION.SDK_INT));
    }

    private void relaunch() {
        Context context = getContext();
        Intent intent =
            new Intent(context, appInfo.launcherActivityName);
        ProcessPhoenix.triggerRebirth(context, intent);
    }

    private void applyAnimationSpeed(int multiplier) {
        try {
            Method method = ValueAnimator.class.getDeclaredMethod("setDurationScale", float.class);
            method.invoke(null, (float) multiplier);
        } catch (Exception e) {
            throw new RuntimeException("Unable to apply animation speed.", e);
        }
    }

    private void setupOkHttpCacheSection() {
        Cache cache = client.cache(); // Shares the cache with apiClient, so no need to check both.
        if (cache != null) {
            binding.debugOkhttpCacheMaxSize.setText(getSizeString(cache.maxSize()));
            refreshOkHttpCacheStats(cache);
        }
    }

    private void refreshOkHttpCacheStats(@NonNull Cache cache) {
        int writeTotal = cache.writeSuccessCount() + cache.writeAbortCount();
        int percentage = (int) ((1f * cache.writeAbortCount() / writeTotal) * 100);
        binding.debugOkhttpCacheWriteError.setText(
            String.format(Locale.ENGLISH, "%d / %d (%d%%)", cache.writeAbortCount(), writeTotal,
                percentage));
        binding.debugOkhttpCacheRequestCount.setText(String.valueOf(cache.requestCount()));
        binding.debugOkhttpCacheNetworkCount.setText(String.valueOf(cache.networkCount()));
        binding.debugOkhttpCacheHitCount.setText(String.valueOf(cache.hitCount()));
    }

    void clearCache() {
        Cache cache = client.cache();
        try {
            if (cache != null) {
                cache.evictAll();
                refreshOkHttpCacheStats(cache);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
