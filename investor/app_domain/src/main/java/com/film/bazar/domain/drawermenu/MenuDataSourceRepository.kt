package com.film.bazar.domain.drawermenu

import com.film.bazar.domain.drawermenu.AppMenus
import io.reactivex.rxjava3.core.Observable

interface MenuDataSourceRepository {
    fun getMenuItems(): Observable<AppMenus>

    fun getAllAppMenu(userType: String): Observable<AppMenus>

    fun saveAppMenusLocal(appMenus: AppMenus)

    fun getSavedMenus(): AppMenus?

    fun getMenus(): AppMenus?


    fun saveBottomMenu(identifier: List<String>): Observable<String>
}