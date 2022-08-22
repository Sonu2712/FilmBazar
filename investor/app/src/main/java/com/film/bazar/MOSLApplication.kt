package com.film.bazar

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import com.jakewharton.processphoenix.ProcessPhoenix
import com.film.app.core.utils.matchesService
import com.film.bazar.coreui.helper.otp.AppSignatureHelper
import com.film.bazar.helper.ApplicationObserver
import com.film.bazar.logger.FileLogger
import dagger.Lazy
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

abstract class MOSLApplication : DaggerApplication() {
    lateinit var appComponent: ApplicationComponent

    @Inject
    lateinit var fileLogger: FileLogger

    private val devKey = "ZZocxenUizQZxo3QD7hef3"

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    @Inject
    lateinit var applicationObserver: ApplicationObserver

    override fun onCreate() {
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return
        }

        if (MissingSplitsManagerFactory.create(this).disableAppIfMissingRequiredSplits()) {
            // Skip app initialization.
            return
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Timber.e(e)
            fileLogger.saveLogs()
            exitProcess(1)
        }

        super.onCreate()
        init()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //=======Get Message Hash Key==============//
        //TODO("Need to comment when app release is pilot")
        val appSignature = AppSignatureHelper(this)
        appSignature.appSignatures

        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(applicationObserver)
        fileLogger.cleanUp()
        Timber.plant(fileLogger.tree())
    }

    abstract fun init()

    override fun getSystemService(name: String): Any? {
        return if (matchesService(name, ApplicationComponent::class.java)) {
            appComponent
        } else super.getSystemService(name)
    }
}