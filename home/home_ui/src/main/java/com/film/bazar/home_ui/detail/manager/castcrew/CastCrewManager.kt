package com.film.bazar.home_ui.detail.manager.castcrew

import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject

class CastCrewManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) {
    private val catsListItem = CastCrewListItem(GroupAdapter())
    private val crewListItem = CastCrewListItem(GroupAdapter())
    fun render(data: CastCrewDetail): Group {
        val section = Section()
        val expandableGroup = ExpandableGroup(CastCrewHeaderTitleItem())
        expandableGroup.add(CastCredDirecterTitleItem(data.directorName))

        expandableGroup.add(CastCrewTitleItem(data.casts.title))
        val castItems = data.casts.casts.map { cast -> CastCrewItem(cast) }
        catsListItem.update(castItems)
        expandableGroup.add(catsListItem)
        expandableGroup.add(ViewLineItem())
        expandableGroup.add(CastCrewTitleItem(data.casts.title))
        val crewItems = data.crews.casts.map { crew -> CastCrewItem(crew) }
        crewListItem.update(crewItems)
        expandableGroup.add(crewListItem)

        section.add(expandableGroup)
        return section
    }
}