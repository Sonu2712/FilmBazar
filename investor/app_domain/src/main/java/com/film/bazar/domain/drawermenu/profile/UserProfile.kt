package com.film.bazar.domain.drawermenu.profile

data class UserAccount(
    val bankName: String,
    val adviceName: String,
    val localizationPermission: String,
    val isNotificationEaabled: Boolean
)

data class UserProfile(
    val mNumber: String,
    val emailId: String,
    val panNumber: String,
    val address: String
)