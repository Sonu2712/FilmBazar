package com.film.bazar.profile.helpsupport.paymentrefund

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.AnswerValue
import com.film.bazar.domain.drawermenu.profile.AnswerYesNo
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface PaymentRefundView : BaseView {
    fun render(uiModel: UiModel<List<AnswerValue>>)
    fun onSubmitClicked() : Observable<Unit>
    fun isNoSelected() : Boolean
    fun showMsg(uiModel: UiModel<String>)
}

class PaymentRefundPresenter @Inject constructor(
    view: PaymentRefundView,
    val repository: ProfileRepository,
    val screenNavigator: ScreenNavigator
) : BasePresenter<PaymentRefundView>(view){

    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getPaymentRefundQandA()
                    .compose(applyUiModel())
            }.subscribe(view::render)
            .addTo(disposable)

        val onSubmitClicked = view.onSubmitClicked().share()
        onSubmitClicked
            .filter { view.isNoSelected() }
            .subscribe {
                screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_WRITE_US_FRAGMENT, true)
            }
            .addTo(disposable)

        onSubmitClicked
            .filter { !view.isNoSelected() }
            .switchMap {
                repository.submitQuery()
                    .compose(applyUiModel())
            }.subscribe {
                view.showMsg(it)
                it.onSuccess {
                    screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_PROFILE_FRAGMENT)
                }
            }
            .addTo(disposable)
    }
}

@Module
abstract class PaymentRefundModule{
    @Binds
    abstract fun providePaymentRefundView(fragment: PaymentRefundFragment) : PaymentRefundView
}