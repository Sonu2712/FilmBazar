package com.film.bazar

import com.film.bazar.home_ui.HomeProvider
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.notification.NotificationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [HomeProvider::class]
)
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun provideNotificationFragment() : NotificationFragment
}