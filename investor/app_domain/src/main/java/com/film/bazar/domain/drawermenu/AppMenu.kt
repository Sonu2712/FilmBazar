package com.film.bazar.domain.drawermenu

sealed class AppMenu constructor(
    val pageId: String,
    val enabled: Boolean = true,
    val secure: Boolean = false,
    val pinkAssistId: Int = 0,
    val isDynamic: Boolean=false,
    val title: String=""
)

class BaseMenu constructor(
    val icon: String,
    val label: String,
    pageId: String = "none",
    enabled: Boolean = true,
    isDynamic: Boolean=false,
    val children: List<ChildMenu> = emptyList()
) : AppMenu(pageId, enabled, false,isDynamic = isDynamic,title = label)

class ChildMenu constructor(
    val label: String,
    val labelStr: String? = null,
    pageId: String = "none",
    enabled: Boolean = true,
    secure: Boolean = false,
    isDynamic:Boolean=false
) : AppMenu(pageId, enabled, secure,isDynamic=isDynamic,title = label)

data class AppMenus(
    @JvmField val baseMenu: List<BaseMenu>,
    @JvmField val bottomMenu: List<UBottomBarMenu>,
    @JvmField val startupScreen: List<StartupScreen>,
    @JvmField val pinkAssist: List<PinkAssist>
)

data class UBottomBarMenu(
    @JvmField val id: String,
    @JvmField val label: String,
    @JvmField val icon: String = "",
    @JvmField var isSelected: Boolean = false,
    @JvmField val isDefault: Boolean
)

data class StartupScreen(
    @JvmField val id: String,
    @JvmField val label: String
)

data class PinkAssist(
    @JvmField val id: String,
    @JvmField val label: String,
    @JvmField val imageUrl: String
)