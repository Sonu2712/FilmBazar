package com.film.bazar.data.notification

import com.film.bazar.domain.drawermenu.notification.NotificationData
import com.film.bazar.domain.drawermenu.notification.NotificationRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor() : NotificationRepository {
    override fun getNotification(): Observable<List<NotificationData>> {
        return Observable.just(mockNotificationData)
    }
}