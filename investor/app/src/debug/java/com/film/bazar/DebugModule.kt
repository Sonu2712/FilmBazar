package com.film.bazar

import android.app.Application
import android.content.Context
import com.film.debugview.DebugViewModule
import com.film.bazar.coredata.DebugBaseDataModule
import com.film.bazar.coredata.DebugDataModule
import com.film.bazar.coreui.CommonUiModule
import com.film.bazar.data.CommonDataModule
import com.film.bazar.data.DebugApiModule
import com.film.bazar.ui.DebugUiModule
import dagger.Binds
import dagger.Module

@Module(
    includes = [CommonDataModule::class,
        DebugApiModule::class,
        DebugDataModule::class,
        DebugBaseDataModule::class,
        DebugViewModule::class,
        DebugUiModule::class,
        CommonUiModule::class]
)
abstract class DebugModule {
    @Binds
    internal abstract fun provideApplication(application: MOSLApplication): Application

    @Binds
    internal abstract fun provideContext(application: Application): Context
}
