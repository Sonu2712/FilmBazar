package com.film.bazar.profile.paymentdetails

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.bazar.domain.drawermenu.profile.UserPaymentDetail
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface PaymentDetailView : BaseView {
    fun renderPaymentDetail(uiModel: UiModel<UserPaymentDetail>)
    fun getPaymentDetail(): UserPaymentDetail
    fun onSaveClicked(): Observable<Unit>
    fun renderSave(uiModel: UiModel<String>)
}

class PaymentDetailPresenter @Inject constructor(
    view: PaymentDetailView,
    val repository: ProfileRepository,
    val screenNavigator: ScreenNavigator
) : BasePresenter<PaymentDetailView>(view) {
    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getUserPaymentDetail()
                    .compose(applyUiModel())
            }.subscribe(view::renderPaymentDetail)
            .addTo(disposable)

        view.onSaveClicked()
            .map { view.getPaymentDetail() }
            .switchMap {
                repository.saveUserPaymentDetails(it)
                    .compose(applyUiModel())
            }.subscribe {
                view.renderSave(it)
                it.onSuccess {
                    screenNavigator.goBack()
                }
            }
    }
}

@Module
abstract class PaymentDetailModule {
    @Binds
    abstract fun providePaymentDetailView(fragment: PaymentDetailsFragment): PaymentDetailView
}