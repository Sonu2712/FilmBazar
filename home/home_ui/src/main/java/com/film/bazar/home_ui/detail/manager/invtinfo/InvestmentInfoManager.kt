package com.film.bazar.home_ui.detail.manager.invtinfo

import com.film.bazar.home_domain.InvestmentInfo
import com.film.bazar.home_ui.TitleItem
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class InvestmentInfoManager {

    fun render(data : List<InvestmentInfo>) : Group{
        val section = Section()
        val titleItem = TitleItem("Investment information", false)
        val groupItem = data.map { InvestmentInfoItem(it) }
        section.add(titleItem)
        section.addAll(groupItem)
        return section
    }
}