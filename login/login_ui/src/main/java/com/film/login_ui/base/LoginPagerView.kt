package com.film.login_ui.base

import com.film.app.core.base.BaseView
import com.film.login_ui.LoginType
import io.reactivex.rxjava3.core.Observable

interface LoginPagerView : BaseView {
    fun onActionSelected(loginType: LoginType)
}
