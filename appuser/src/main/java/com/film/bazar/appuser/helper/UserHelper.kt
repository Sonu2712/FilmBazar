package com.film.bazar.appuser.helper

import android.content.Context
import android.content.SharedPreferences
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.appusercore.model.UserType

fun Context.getPref(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun UserType.canTrade(): Boolean {
    return this == UserType.TRADING
}

fun UserManager.userIsEmployee(): Boolean {
    return getUser()?.userConfig?.isStaff ?: false
}

fun UserManager.userIsNotEmployee(): Boolean {
    return !userIsEmployee()
}
