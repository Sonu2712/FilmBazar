package com.film.bazar.profile

import com.film.bazar.profile.editprofile.EditProfileFragment
import com.film.bazar.profile.helpsupport.HelpSupportFragment
import com.film.bazar.profile.helpsupport.HelpSupportModule
import com.film.bazar.profile.helpsupport.paymentrefund.PaymentRefundFragment
import com.film.bazar.profile.helpsupport.paymentrefund.PaymentRefundModule
import com.film.bazar.profile.helpsupport.writeus.WriteUsFragment
import com.film.bazar.profile.paymentdetails.PaymentDetailsFragment
import com.film.bazar.profile.termscondition.TermsConditionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileProvider {
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun provideProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun provideEditProfileFragment() : EditProfileFragment

    @ContributesAndroidInjector(modules = [HelpSupportModule::class])
    abstract fun provideHelpSupportFragment() : HelpSupportFragment

    @ContributesAndroidInjector(modules = [PaymentRefundModule::class])
    abstract fun providePaymentRefundFragment() : PaymentRefundFragment

    @ContributesAndroidInjector
    abstract fun provideTermsConditionFragment() : TermsConditionFragment

    @ContributesAndroidInjector
    abstract fun providePaymentDetailsFragment() : PaymentDetailsFragment

    @ContributesAndroidInjector
    abstract fun provideWriteUsFragment() : WriteUsFragment
}