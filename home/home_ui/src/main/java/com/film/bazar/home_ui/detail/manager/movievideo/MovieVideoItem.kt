package com.film.bazar.home_ui.detail.manager.movievideo

import android.view.View
import coil.api.load
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.VideoInfo
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemVideosBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieVideoItem(
    val data : VideoInfo
) : ViewBindingItem<ItemVideosBinding>(){

    override fun getLayout(): Int {
        return R.layout.item_videos
    }

    override fun initBinding(itemView: View): ItemVideosBinding {
        return ItemVideosBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemVideosBinding, position: Int) {
        viewBinding.apply {
            ivYoutubeView.load(data.thumbnail)
            tvVideoTitle.text = data.title
        }
    }
}