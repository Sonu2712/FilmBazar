package com.film.bazar.profile

import com.film.bazar.profile.editprofile.EditProfileFragment
import com.film.bazar.profile.helpsupport.HelpSupportFragment
import com.film.bazar.profile.helpsupport.HelpSupportModule
import com.film.bazar.profile.helpsupport.questionanswer.QuestionAnswerFragment
import com.film.bazar.profile.helpsupport.questionanswer.PaymentRefundModule
import com.film.bazar.profile.helpsupport.writeus.WriteToUsModule
import com.film.bazar.profile.helpsupport.writeus.WriteUsFragment
import com.film.bazar.profile.helpsupport.writeus.confirmation.ConfirmationBottomSheetFragment
import com.film.bazar.profile.helpsupport.writeus.confirmation.ConfirmationBottomSheetModule
import com.film.bazar.profile.paymentdetails.PaymentDetailsFragment
import com.film.bazar.profile.termscondition.TermsConditionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileProvider {
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun provideProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun provideEditProfileFragment(): EditProfileFragment

    @ContributesAndroidInjector(modules = [HelpSupportModule::class])
    abstract fun provideHelpSupportFragment(): HelpSupportFragment

    @ContributesAndroidInjector(modules = [PaymentRefundModule::class])
    abstract fun providePaymentRefundFragment(): QuestionAnswerFragment

    @ContributesAndroidInjector
    abstract fun provideTermsConditionFragment(): TermsConditionFragment

    @ContributesAndroidInjector
    abstract fun providePaymentDetailsFragment(): PaymentDetailsFragment

    @ContributesAndroidInjector(modules = [WriteToUsModule::class])
    abstract fun provideWriteUsFragment(): WriteUsFragment

    @ContributesAndroidInjector(modules = [ConfirmationBottomSheetModule::class])
    abstract fun provideConfirmationBottomSheetFragment(): ConfirmationBottomSheetFragment
}