package com.film.bazar.home_ui.moviebanner

import com.film.bazar.home_domain.MovieBanner
import com.mosl.mobile.coreui.item.CarouselItem
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section

class MovieBannerManager {
    val carouselItem: CarouselItem by lazy {
        CarouselItem(GroupAdapter())
    }
    fun render(movieInfo: List<MovieBanner>): Group {
        val section = Section()
        section.add(carouselItem)
        val items = movieInfo.map { MovieBannerItem(it) }
        carouselItem.update(items)
        return section
    }
}