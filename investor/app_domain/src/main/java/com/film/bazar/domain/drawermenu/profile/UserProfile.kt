package com.film.bazar.domain.drawermenu.profile

class UserProfile {
}

data class UserAccount(
    val bankName : String,
    val adviceName : String,
    val localizationPermission : String,
    val isNotificationEaabled : Boolean
)