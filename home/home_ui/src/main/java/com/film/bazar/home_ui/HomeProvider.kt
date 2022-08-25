package com.film.bazar.home_ui

import com.film.bazar.home_ui.sortfilter.SortFilterBottomSheetFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeProvider {
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun provideHomeFragmentFactory(): HomeFragment

    @ContributesAndroidInjector
    abstract fun provideSortFilterBottomSheetFragment() : SortFilterBottomSheetFragment
}
