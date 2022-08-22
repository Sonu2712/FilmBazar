package com.film.bazar

import com.film.bazar.home_ui.HomeProvider
import dagger.Module

@Module(
    includes = [HomeProvider::class]
)
abstract class FragmentProvider {

}