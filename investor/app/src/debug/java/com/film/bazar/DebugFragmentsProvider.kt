package com.film.bazar

import com.film.bazar.dashboard.DashboardFragment
import com.film.bazar.dashboard.DashboardModule
import com.film.bazar.drawermenu.DrawerMenuFragment
import com.film.bazar.featurescope.FirstPlaygroundFragment
import com.film.bazar.featurescope.SecondPlaygroundFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DebugFragmentsProvider {
  @ContributesAndroidInjector
  abstract fun provideFirstPlaygroundFragmentFactory(): FirstPlaygroundFragment

  @ContributesAndroidInjector
  abstract fun provideDeeplinkTestFragmentFactory(): SecondPlaygroundFragment

  @ContributesAndroidInjector(modules = [DashboardModule::class])
  abstract fun provideDashboardFragmentFactory(): DashboardFragment

  @ContributesAndroidInjector
  abstract fun provideDrawerMenuFragmentFactory(): DrawerMenuFragment
}
