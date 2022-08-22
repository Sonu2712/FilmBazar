package com.film.bazar.data.drawermenu

import com.film.bazar.domain.drawermenu.PinkAssist

data class LocalAppMenu(
    val allMenus: List<AllMenu>,
    val bottomMenus: List<BottomMenu>,
    val startUpMenus: List<StartUpMenu>,
    val pinkAssist: List<PinkAssist>
)