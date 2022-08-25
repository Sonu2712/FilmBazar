package com.film.bazar.domain.drawermenu.notification

data class NotificationData(
    @JvmField val imageUrl : String,
    @JvmField val title : String,
    @JvmField val subTitle : String,
    @JvmField val daysTime : String
)