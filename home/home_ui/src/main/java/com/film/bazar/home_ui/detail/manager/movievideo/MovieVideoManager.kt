package com.film.bazar.home_ui.detail.manager.movievideo

import android.view.View
import com.film.bazar.home_domain.VideoInfo
import com.film.bazar.home_ui.HomeNavEvent
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.TitleItem
import com.film.bazar.home_ui.onNext
import com.mosl.mobile.coreui.item.CarouselItem
import com.xwray.groupie.*
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieVideoManager(
    val uiEvent: PublishSubject<HomeUiEvent>
) : OnItemClickListener {
    val carouselItem: CarouselItem by lazy {
        val recoCarouselAdapter = GroupAdapter<GroupieViewHolder>()
        recoCarouselAdapter.setOnItemClickListener(this)
        CarouselItem(groupAdapter = recoCarouselAdapter)
    }

    fun render(data : List<VideoInfo>) : Group {
        val section = Section()
        val titleItem = TitleItem("Videos", true)
        val groupItem = data.map { MovieVideoItem(it) }
        section.add(titleItem)
        section.add(carouselItem)
        carouselItem.update(groupItem)
        return section
    }

    override fun onItemClick(item: Item<*>, view: View) {
        if (item is MovieVideoItem){
            uiEvent.onNext(HomeNavEvent.OpenVideo(item.data.videoId))
        }
    }
}