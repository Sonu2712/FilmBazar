package com.film.bazar

import android.app.Application
import android.content.Context
import com.film.bazar.coredata.DataModule
import com.film.bazar.coreui.CommonUiModule
import com.film.bazar.data.ApiModule
import com.film.bazar.data.CommonDataModule
import dagger.Binds
import dagger.Module

@Module(includes = [UiModule::class, CommonUiModule::class, DataModule::class, CommonDataModule::class, ApiModule::class])
abstract class ApplicationModule {
    @Binds
    internal abstract fun provideApplication(application: MOSLApplication): Application

    @Binds
    internal abstract fun provideContext(application: Application): Context
}
