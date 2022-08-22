package com.film.bazar.data

import com.film.bazar.data.drawermenu.MenuDataSourceRepositoryImpl
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.home_data.repository.HomeRepositoryProvider
import dagger.Binds
import dagger.Module

@Module(
    includes = [HomeRepositoryProvider::class]
)
abstract class RepositoryAliasProvider {
    @Binds
    abstract fun provideMenuDataSourceRepository(repository: MenuDataSourceRepositoryImpl): MenuDataSourceRepository
}
