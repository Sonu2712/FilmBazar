package com.film.bazar.profile.helpsupport

import com.film.app.core.base.BaseView
import io.reactivex.rxjava3.core.Observable

interface HelpSupportView : BaseView {
    fun onPaymentRefundClicked() : Observable<Unit>
    fun onAccountRelatedClicked() : Observable<Unit>
    fun onWalletClicked() : Observable<Unit>
    fun onInvestmentClicked() : Observable<Unit>
    fun onWritUsClicked() : Observable<Unit>
    fun showMessage(msg : String)
}