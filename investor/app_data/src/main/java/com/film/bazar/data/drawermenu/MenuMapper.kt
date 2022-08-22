package com.film.bazar.data.drawermenu

import com.film.bazar.domain.drawermenu.*
import com.film.bazar.appusercore.model.AppLanguage

fun LocalAppMenu.transform1(language: AppLanguage) : AppMenus {
    val drawerMenu = allMenus.map { baseMenu ->
        BaseMenu(
            pageId = baseMenu.identifier.trim(),
            icon = baseMenu.icon ?: "",
            label = if (language.isHindi()) baseMenu.titleHi else if (language.isGujrati()) baseMenu.titleGu else baseMenu.title ,
            enabled = baseMenu.isEnabled,
            isDynamic = baseMenu.isDynamic,
            children = baseMenu.items?.map { childMenu ->
                ChildMenu(
                    label = if (language.isHindi()) childMenu.titleHi else if (language.isGujrati()) childMenu.titleGu else childMenu.title,
                    pageId = childMenu.identifier.trim(),
                    enabled = childMenu.isEnabled,
                    isDynamic = childMenu.isDynamic
                )
            } ?: emptyList()
        )
    }

    val bottomMenu = bottomMenus.map {
        UBottomBarMenu(
            id = it.id.trim(),
            label = it.title,
            icon = it.icon?: "",
            isSelected = false,
            isDefault = it.isDefault
        )
    }

    val startUpMenu = startUpMenus.map {
        StartupScreen(
            id = it.id.trim(),
            label = if (language.isHindi()) it.titleHi else if (language.isGujrati()) it.titleGu else it.title
        )
    }

    val pinkAssist = pinkAssist.map{
        PinkAssist(it.id, it.label, it.imageUrl)
    }


    return AppMenus(
        baseMenu = drawerMenu,
        bottomMenu = bottomMenu,
        startupScreen = startUpMenu,
        pinkAssist = pinkAssist
    )
}