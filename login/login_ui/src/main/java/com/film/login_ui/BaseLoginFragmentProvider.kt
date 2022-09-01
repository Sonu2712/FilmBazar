package com.film.login_ui

import com.film.login_ui.base.LoginBaseFragment
import com.film.login_ui.customer.CustomerLoginFragment
import com.film.login_ui.customer.CustomerLoginModule
import com.film.login_ui.forgotpassword.ForgotPasswordBottomSheetFragment
import com.film.login_ui.forgotpassword.ForgotPasswordModule
import com.film.login_ui.pager.LoginBaseModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BaseLoginFragmentProvider {
    @ContributesAndroidInjector(modules = [CustomerLoginModule::class])
    abstract fun provideCustomerLoginFragmentFactory(): CustomerLoginFragment

    @ContributesAndroidInjector(modules = [ForgotPasswordModule::class])
    abstract fun provideForgotPasswordBottomSheetFragment() : ForgotPasswordBottomSheetFragment

    @ContributesAndroidInjector(modules = [LoginBaseModule::class])
    abstract fun provideLoginPagerFragmentFactory(): LoginBaseFragment
}
