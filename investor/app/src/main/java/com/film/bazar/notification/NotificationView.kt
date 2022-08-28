package com.film.bazar.notification

import com.film.app.core.base.BaseView
import com.film.bazar.domain.drawermenu.notification.NotificationData
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface NotificationView : BaseView {
    fun render(uiModel: UiModel<List<NotificationData>>)
    fun onOkayClicked() : Observable<Unit>
}