package com.film.bazar.home_ui.movietab

import com.film.bazar.coreui.tabs.SimpleTabListener
import com.film.bazar.home_domain.MovieTab
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.Group
import com.xwray.groupie.Section

class MovieTabManager() {
    var currentTab: MovieTab = MovieTab.OngoingProject
    lateinit var movieTabItem: MovieTabItem

    fun render(tab : List<MovieTab>): Group {
        val section = Section()
        movieTabItem = MovieTabItem(
            tabs = tab,
            tabListener = object : SimpleTabListener() {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    val selectedtab = tab.tag as MovieTab
                    if(!currentTab.equals(selectedtab)){
                        currentTab = selectedtab
                        movieTabItem.updatePosition(currentTab.toString())
                        /*uiEvents.onNext(
                            HomeUiEvent.OnMarketsTabChanged(selectedtab.toString())
                        )*/
                    }
                }
            })
        section.add(movieTabItem)
        section.setFooter(MovieFilterItem())
        return section
    }
}