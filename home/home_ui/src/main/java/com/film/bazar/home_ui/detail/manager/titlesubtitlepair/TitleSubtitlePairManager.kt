package com.film.bazar.home_ui.detail.manager.titlesubtitlepair

import com.film.bazar.home_domain.TitleSubTitle
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class TitleSubtitlePairManager {

    fun render(data : List<TitleSubTitle>) : Group{
        val section = Section()
        val groupItem = data.map { TitleSubtitlePairItem(it) }
        section.update(groupItem)
        return section
    }
}