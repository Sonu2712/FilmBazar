package com.film.bazar.profile.helpsupport

import com.film.app.core.base.BasePresenter
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.commons.rx.addTo
import javax.inject.Inject

class HelpSupportPresenter @Inject constructor(
    view: HelpSupportView,
    val screenNavigator: ScreenNavigator
) : BasePresenter<HelpSupportView>(view) {
    override fun start() {
        view.onPaymentRefundClicked()
            .subscribe {
                screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_PAYMENT_REFUND_FRAGMENT, true)
            }.addTo(disposable)

        view.onAccountRelatedClicked()
            .map { "Account Related" }
            .subscribe(view::showMessage)
            .addTo(disposable)

        view.onWalletClicked()
            .map { "Wallet" }
            .subscribe(view::showMessage)
            .addTo(disposable)

        view.onInvestmentClicked()
            .map { "Investment" }
            .subscribe(view::showMessage)
            .addTo(disposable)

        view.onWritUsClicked()
            .map { "Write to us" }
            .subscribe(view::showMessage)
            .addTo(disposable)
    }
}