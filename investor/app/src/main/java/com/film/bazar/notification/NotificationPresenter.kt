package com.film.bazar.notification

import com.film.app.core.base.BasePresenter
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.notification.NotificationRepository
import com.film.commons.rx.addTo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NotificationPresenter @Inject constructor(
    view : NotificationView,
    val repository: NotificationRepository,
    val screenNavigator: ScreenNavigator
) : BasePresenter<NotificationView>(view = view){

    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getNotification()
                    .compose(applyUiModel())
            }.subscribe(view::render)
            .addTo(disposable)

        view.onOkayClicked()
            .subscribe {
                screenNavigator.goBack()
            }.addTo(disposable)
    }
}