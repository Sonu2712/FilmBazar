package com.film.bazar.home_data.repository

import com.film.bazar.home_data.repository.HomeRepositoryImpl
import com.film.bazar.home_domain.HomeRepository
import dagger.Binds
import dagger.Module

@Module
abstract class HomeRepositoryProvider {
    @Binds
    internal abstract fun providerHomeRepository(repository: HomeRepositoryImpl): HomeRepository
}
