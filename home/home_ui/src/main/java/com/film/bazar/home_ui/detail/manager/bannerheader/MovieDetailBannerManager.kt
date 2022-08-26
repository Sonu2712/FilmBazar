package com.film.bazar.home_ui.detail.manager.bannerheader

import com.film.bazar.home_domain.MovieDetailBannerInfo
import com.film.bazar.home_ui.HomeUiEvent
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieDetailBannerManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) {
    fun render(movieInfo: MovieDetailBannerInfo): Group {
        val section = Section()
        val items = MovieDetailBannerItem(movieInfo, uiEvent)
        section.add(items)
        return section
    }
}