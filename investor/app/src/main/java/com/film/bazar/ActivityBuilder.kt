package com.film.bazar

import com.film.annotations.ActivityScoped
import com.film.bazar.core.CommonActivityModule
import com.film.bazar.core.MOSLCommonActivity
import com.film.bazar.core.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [CommonActivityBuilder::class])
abstract class ActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [CommonActivityModule::class, MainActivityModule::class])
    internal abstract fun bindCommonActivity(): MOSLCommonActivity
}
