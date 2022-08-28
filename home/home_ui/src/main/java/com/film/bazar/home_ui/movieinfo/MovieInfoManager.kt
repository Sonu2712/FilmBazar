package com.film.bazar.home_ui.movieinfo

import com.film.bazar.coreui.tabs.SimpleTabListener
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_domain.MovieModel
import com.film.bazar.home_domain.MovieTab
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.movietab.MovieTabItem
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieInfoManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) {
    fun render(movieInfo: List<MovieInfo>): Group {
        val section = Section()
        section.clear()
        val items = movieInfo.map { MovieItem(it, uiEvent) }
        section.update(items)
        return section
    }
}