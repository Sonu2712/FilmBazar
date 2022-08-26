package com.film.bazar

import com.film.bazar.home_ui.HomeProvider
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.notification.NotificationModule
import com.film.bazar.profile.ProfileFragment
import com.film.bazar.profile.ProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [HomeProvider::class]
)
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun provideNotificationFragment() : NotificationFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun provideProfileFragment() : ProfileFragment
}