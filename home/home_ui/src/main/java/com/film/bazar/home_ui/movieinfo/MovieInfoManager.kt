package com.film.bazar.home_ui.movieinfo

import com.film.bazar.home_domain.MovieInfo
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class MovieInfoManager() {
    fun render(movieInfo: List<MovieInfo>): Group {
        val section = Section()
        val items = movieInfo.map { MovieItem(it) }
        section.update(items)
        return section
    }
}