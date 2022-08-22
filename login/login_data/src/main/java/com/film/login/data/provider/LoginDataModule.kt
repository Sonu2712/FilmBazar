package com.film.login.data.provider

import com.film.login.data.repository.AppUserRepository
import com.film.login.data.repository.AppUserRepositoryImpl
import com.film.login.data.repository.LoginRepository
import com.film.login.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LoginDataModule {
    @Binds
    internal abstract fun provideAppUserRepository(repository: AppUserRepositoryImpl): AppUserRepository

    @Binds
    abstract fun provideLoginRepository(repository: LoginRepositoryImpl): LoginRepository
}
