package com.film.bazar.home_ui.detail.manager.castcrew

import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

class CastCrewManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) {
    val section = Section()
    lateinit var castCrewHeaderTitleItem : CastCrewHeaderTitleItem
    fun render(data : CastCrewDetail) : Group{
        val section = Section()
        castCrewHeaderTitleItem = CastCrewHeaderTitleItem(uiEvent)
        section.add(CastCrewHeaderTitleItem(uiEvent))
        return section
    }

    fun renderCastCrewDetail(data: CastCrewDetail){
         //val castCrewHeaderItem = data.map { CastCrewHeaderItem(it, uiEvent)}
        castCrewHeaderTitleItem.renderCastDetail(data)
        Timber.d("Cast Crew clicked")
    }
}