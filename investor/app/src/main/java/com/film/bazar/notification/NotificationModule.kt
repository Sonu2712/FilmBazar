package com.film.bazar.notification

import dagger.Binds
import dagger.Module

@Module
abstract class NotificationModule {
    @Binds
    abstract fun provideNotificationFragment(fragment: NotificationFragment) : NotificationView
}