package com.film.bazar

import com.film.bazar.home_ui.HomeProvider
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.notification.NotificationModule
import com.film.bazar.portfolio.PortfolioFragment
import com.film.bazar.profile.ProfileProvider
import com.film.bazar.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [HomeProvider::class, ProfileProvider::class]
)
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun provideNotificationFragment() : NotificationFragment

    @ContributesAndroidInjector
    abstract fun providePortfolioFragment() : PortfolioFragment

    @ContributesAndroidInjector
    abstract fun provideWalletFragment() : WalletFragment
}