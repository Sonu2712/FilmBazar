package com.film.bazar.domain.drawermenu.profile

import io.reactivex.rxjava3.core.Observable

interface ProfileRepository {
    fun getHelpSupportQuestions() : Observable<List<HelpSupportQuestions>>
    fun getPaymentRefundQandA(questionId : Int): Observable<List<AnswerValue>>
    fun submitQuery() : Observable<String>
    fun writeToUs(message : String) : Observable<String>
}