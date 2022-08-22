package com.film.bazar.drawermenu.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.film.bazar.constants.NavigationConstants

sealed class AppMenu constructor(
    @NavigationConstants val pageId: String,
    val enabled: Boolean = true,
    val secure: Boolean = false
)

class BaseMenu constructor(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    pageId: String = NavigationConstants.NAVIGATE_TO_NONE,
    enabled: Boolean = true,
    val children: List<ChildMenu> = emptyList()
) : AppMenu(pageId, enabled, false)

class ChildMenu constructor(
    @StringRes val label: Int,
    val labelStr: String? = null,
    pageId: String = NavigationConstants.NAVIGATE_TO_NONE,
    enabled: Boolean = true,
    secure: Boolean = false
) : AppMenu(pageId, enabled, secure)

class OtherMenu(pageId: String) : AppMenu(pageId, enabled = true, secure = false)

class DarkModeSwitchMenu(pageId: String, val data: Boolean) :
  AppMenu(pageId, enabled = true, secure = false)
