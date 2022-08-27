package com.film.bazar.data.profile

import com.film.bazar.domain.drawermenu.profile.*
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    override fun saveNotificationStatus(isEnabled: Boolean): Observable<String> {
        return Observable.just("Saved Successfully $isEnabled")
    }

    override fun getUserProfile(): Observable<UserProfile> {
        return Observable.just(
            UserProfile(
                mNumber = "9987503045",
                emailId = "milindh24@gmail.com",
                panNumber = "AFJPH6357H",
                address = "001, 15-A, Aarti, Sevenstar colony, Gokuldham, Goregaon (E), Mumbai - 400063"
            )
        )
    }

    override fun saveUserProfile(profile: UserProfile): Observable<String> {
        return Observable.just("Saved Successfully")
    }

    override fun getUserPaymentDetail(): Observable<UserPaymentDetail> {
        return Observable.just(
            UserPaymentDetail(
                bankName = "HDFC Bank",
                accHolderName = "Milind Anand Haldankar",
                accNumber = "001246567903290",
                ifscCode = "HDFC000012"
            )
        )
    }

    override fun saveUserPaymentDetails(data: UserPaymentDetail): Observable<String> {
        return Observable.just("Saved Successfully")
    }

    override fun getHelpSupportQuestions(): Observable<List<HelpSupportQuestions>> {
        return Observable.just(
            listOf(
                HelpSupportQuestions(
                    questionId = 1,
                    questionLabel = "Payment & refund"
                ),
                HelpSupportQuestions(
                    questionId = 2,
                    questionLabel = "Account related"
                ),
                HelpSupportQuestions(
                    questionId = 3,
                    questionLabel = "Wallet"
                ),
                HelpSupportQuestions(
                    questionId = 4,
                    questionLabel = "Investment"
                )
            )
        )
    }

    override fun getPaymentRefundQandA(questionId: Int): Observable<List<AnswerValue>> {
        return Observable.just(
            listOf(
                AnswerValue(
                    id = 1,
                    key = "Officia deserunt mollit anim id est ",
                    value = listOf(
                        "1. Go to the 'My Account' section in the app.",
                        "2. Select 'My Plan'. Here you can view all details related to your existing plan.",
                        "3. Click on 'Renew'. You can choose to renew the same plan or upgrade to another plan.",
                        "On making the payment, your renewal process will be complete."
                    )
                ),
                AnswerValue(
                    id = 2,
                    key = "Dolore magna aliqua",
                    value = listOf(
                        "1. Go to the 'My Account' section in the app.",
                        "2. Select 'My Plan'. Here you can view all details related to your existing plan.",
                        "3. Click on 'Renew'. You can choose to renew the same plan or upgrade to another plan.",
                        "On making the payment, your renewal process will be complete."
                    )
                ),
                AnswerValue(
                    id = 3,
                    key = "Duis aute irure dolor in reprehen",
                    value = listOf(
                        "1. Go to the 'My Account' section in the app.",
                        "2. Select 'My Plan'. Here you can view all details related to your existing plan.",
                        "3. Click on 'Renew'. You can choose to renew the same plan or upgrade to another plan.",
                        "On making the payment, your renewal process will be complete."
                    )
                ),
                AnswerValue(
                    id = 4,
                    key = "Excepteur sint occaecat cupidatat non",
                    value = listOf(
                        "1. Go to the 'My Account' section in the app.",
                        "2. Select 'My Plan'. Here you can view all details related to your existing plan.",
                        "3. Click on 'Renew'. You can choose to renew the same plan or upgrade to another plan.",
                        "On making the payment, your renewal process will be complete."
                    )
                )
            )
        )
    }

    override fun submitQuery(): Observable<String> {
        return Observable.just("Thanks for your feedback")
    }

    override fun writeToUs(message: String): Observable<String> {
        return Observable.just("Your ticket number is QRT884995")
    }
}