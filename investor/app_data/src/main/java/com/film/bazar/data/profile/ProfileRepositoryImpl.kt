package com.film.bazar.data.profile

import com.film.bazar.domain.drawermenu.profile.AnswerValue
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {
    override fun getPaymentRefundQandA(): Observable<List<AnswerValue>> {
        return Observable.just(listOf(
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
        ))
    }

    override fun submitQuery(): Observable<String> {
        return Observable.just("Thanks for your feedback")
    }
}