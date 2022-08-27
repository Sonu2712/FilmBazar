package com.film.bazar.domain.drawermenu.profile

import io.reactivex.rxjava3.core.Observable

interface ProfileRepository {
    fun getPaymentRefundQandA(): Observable<List<AnswerValue>>
    fun submitQuery() : Observable<String>
    fun writeToUs(message : String) : Observable<String>
}