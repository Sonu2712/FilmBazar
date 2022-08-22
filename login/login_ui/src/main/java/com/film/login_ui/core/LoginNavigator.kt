package com.film.login_ui.core

import android.content.Context
import android.os.Bundle
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.coreui.navigatorlib.BaseNavigator

interface LoginNavigator : BaseNavigator {
    fun forceChangePassword(targetPageId: String, bundle: Bundle)
    fun login(userType: UserType)
    fun finish()
    fun openUrl(context: Context, url: String)
    fun navigateToMarkets()
}
