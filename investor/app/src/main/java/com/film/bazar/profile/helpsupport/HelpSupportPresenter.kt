package com.film.bazar.profile.helpsupport

import android.os.Bundle
import com.film.app.core.base.BasePresenter
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.bazar.profile.helpsupport.questionanswer.PaymentRefundView.Companion.ARG_QUESTION_ID
import com.film.bazar.profile.helpsupport.questionanswer.PaymentRefundView.Companion.ARG_QUESTION_TITLE
import com.film.commons.rx.addTo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class HelpSupportPresenter @Inject constructor(
    view: HelpSupportView,
    val screenNavigator: ScreenNavigator,
    val repository: ProfileRepository
) : BasePresenter<HelpSupportView>(view) {
    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getHelpSupportQuestions()
                    .compose(applyUiModel())
            }.subscribe(view::render)
            .addTo(disposable)

        view.onItemClicked()
            .subscribe {
                val args = Bundle().apply {
                    putInt(ARG_QUESTION_ID, it.id)
                    putString(ARG_QUESTION_TITLE, it.label)
                }
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_PAYMENT_REFUND_FRAGMENT,
                    args,
                    true
                )
            }.addTo(disposable)

        view.onWritUsClicked()
            .map { "Write to us" }
            .subscribe {
                screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_WRITE_US_FRAGMENT, true)
            }
            .addTo(disposable)

        view.onCallClicked()
            .subscribe {
                view.callToTradePopup()
            }.addTo(disposable)

        view.onChatClicked()
            .subscribe {
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_CHAT_WITH_US_FRAGMENT,
                    true
                )
            }.addTo(disposable)
    }
}