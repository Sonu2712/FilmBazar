package com.film.bazar.home_ui.detail.manager.bannerheader

import com.film.bazar.home_domain.MovieDetailBannerInfo
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class MovieDetailBannerManager {
    fun render(movieInfo: MovieDetailBannerInfo): Group {
        val section = Section()
        val items = MovieDetailBannerItem(movieInfo)
        section.add(items)
        return section
    }
}