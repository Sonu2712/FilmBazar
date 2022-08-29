package com.film.bazar.home_ui.movietab

import com.film.bazar.coreui.tabs.SimpleTabListener
import com.film.bazar.home_domain.MovieTab
import com.film.bazar.home_ui.HomeUiEvent
import com.google.android.material.tabs.TabLayout
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieTabManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) {
    var currentTab: MovieTab = MovieTab.ONGOING_PROJECT
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
                        uiEvent.onNext(HomeUiEvent.MovieTabChanged(currentTab))
                    }
                }
            }, uiEvent = uiEvent)
        section.add(movieTabItem)
        return section
    }
}