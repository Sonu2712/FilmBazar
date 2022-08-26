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
    var currentTab: MovieTab = MovieTab.OngoingProject
    lateinit var movieModel: MovieModel
    lateinit var movieTabItem: MovieTabItem
    fun render(movieInfo: MovieModel): Group {
        this.movieModel = movieInfo
        movieTabItem = MovieTabItem(
            tabs = movieInfo.tab,
            tabListener = object : SimpleTabListener() {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val selectedTab = tab.tag as MovieTab
                    if(currentTab != selectedTab){
                        currentTab = selectedTab
                        movieTabItem.updatePosition(currentTab.toString())
                    }
                }
            },
            uiEvent = uiEvent
        )
        val section = Section()
        val items = movieInfo.info.map { MovieItem(it, uiEvent) }
        section.setHeader(movieTabItem)
        section.update(items)
        return section
    }
}