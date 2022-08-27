package com.film.bazar

import com.film.bazar.home_ui.HomeProvider
import com.film.bazar.notification.NotificationFragment
import com.film.bazar.notification.NotificationModule
import com.film.bazar.portfolio.PortfolioFragment
import com.film.bazar.profile.ProfileFragment
import com.film.bazar.profile.ProfileModule
import com.film.bazar.profile.editprofile.EditProfileFragment
import com.film.bazar.profile.helpsupport.HelpSupportFragment
import com.film.bazar.profile.paymentdetails.PaymentDetailsFragment
import com.film.bazar.profile.termscondition.TermsConditionFragment
import com.film.bazar.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [HomeProvider::class]
)
abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    abstract fun provideNotificationFragment() : NotificationFragment

    @ContributesAndroidInjector
    abstract fun providePortfolioFragment() : PortfolioFragment

    @ContributesAndroidInjector
    abstract fun provideWalletFragment() : WalletFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun provideProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun provideEditProfileFragment() : EditProfileFragment

    @ContributesAndroidInjector
    abstract fun provideHelpSupportFragment() : HelpSupportFragment

    @ContributesAndroidInjector
    abstract fun provideTermsConditionFragment() : TermsConditionFragment

    @ContributesAndroidInjector
    abstract fun providePaymentDetailsFragment() : PaymentDetailsFragment
}