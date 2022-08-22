package com.film.login_ui.customer

import com.film.bazar.appusercore.model.UserType
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CustomerLoginModule {
    @Binds
    abstract fun provideLoginView(fragment: CustomerLoginFragment): LoginView

    companion object {
        @Provides
        fun provideUserType(): LoginPageData {
            return LoginPageData(UserType.TRADING)
        }
    }
}
