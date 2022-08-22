package com.film.bazar

import com.film.annotations.ActivityScoped
import com.film.bazar.core.CommonActivityModule
import com.film.bazar.core.MOSLCommonActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [CommonActivityBuilder::class])
abstract class DebugActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [CommonActivityModule::class, DebugFragmentsProvider::class, DebugActivityModule::class]
    )
    abstract fun bindCommonActivity(): MOSLCommonActivity
}
