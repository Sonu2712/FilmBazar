package com.film.bazar

import android.content.Context
import androidx.multidex.MultiDex
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.jakewharton.threetenabp.AndroidThreeTen
import com.film.app.core.utils.matchesService
import com.film.debugview.DebugComponent
import com.film.debugview.LumberYard
import com.film.bazar.coredata.DataModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject

class MOSLApplicationImpl : MOSLApplication(), androidx.work.Configuration.Provider {
    @Inject
    lateinit var lumberYard: LumberYard

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build().also {
                this.appComponent = it
            }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Timber.i("onLowMemory triggered")
    }

    override fun init() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return element.lineNumber.toString() + ":" + super.createStackElementTag(element)
            }
        })

        AndroidThreeTen.init(this)
        RxJavaPlugins.setErrorHandler { throwable -> Timber.tag("RxError").e(throwable) }

        val logger = HttpLoggingInterceptor(object  : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Timber.tag("Coil").d(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.BASIC }

        val context = this
        Coil.setDefaultImageLoader {
            ImageLoader(context) {
                crossfade(true)
                allowHardware(false)
                okHttpClient {
                    DataModule.createOkHttpClient(CertPinProviderImpl.getPinner())
                        .addInterceptor(logger)
                        .cache(CoilUtils.createDefaultCache(context))
                        .build()
                }
            }
        }

        lumberYard.cleanUp()
        Timber.plant(lumberYard.tree())
    }

    override fun getSystemService(name: String): Any? {
        return if (matchesService(name, DebugComponent::class.java)) {
            appComponent
        } else super.getSystemService(name)
    }

    override fun getWorkManagerConfiguration(): androidx.work.Configuration {
        return androidx.work.Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }
}