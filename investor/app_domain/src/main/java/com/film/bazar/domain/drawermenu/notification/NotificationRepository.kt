package com.film.bazar.domain.drawermenu.notification

import io.reactivex.rxjava3.core.Observable

interface NotificationRepository {
    fun getNotification() : Observable<List<NotificationData>>
}