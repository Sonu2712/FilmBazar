package com.film.login_ui.base

import com.film.app.core.base.BaseView
import com.film.login_ui.LoginType
import io.reactivex.rxjava3.core.Observable

interface LoginPagerView : BaseView {
    fun getFirstRowAction(): LoginType
    fun getSecondRowAction(): LoginType
    fun onFirstRowActionClicked(): Observable<Unit>
    fun onSecondRowActionClicked(): Observable<Unit>
    fun onActionSelected(loginType: LoginType)
}
