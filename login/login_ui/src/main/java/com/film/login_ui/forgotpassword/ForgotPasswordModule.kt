package com.film.login_ui.forgotpassword

import dagger.Binds
import dagger.Module

@Module
abstract class ForgotPasswordModule {
    @Binds
    abstract fun provideForgotPasswordView(fragment: ForgotPasswordBottomSheetFragment) : ForgotPasswordView
}