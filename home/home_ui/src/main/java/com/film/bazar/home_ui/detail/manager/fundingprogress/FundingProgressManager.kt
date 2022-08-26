package com.film.bazar.home_ui.detail.manager.fundingprogress

import com.film.bazar.home_domain.MovieFund
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class FundingProgressManager {
    fun render(data : MovieFund) : Group{
        val section = Section()
        val groupItem = FundingProgressItem(data)
        section.add(groupItem)
        return section
    }
}