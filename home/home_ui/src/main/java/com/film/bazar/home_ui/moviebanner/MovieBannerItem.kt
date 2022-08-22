package com.film.bazar.home_ui.moviebanner

import android.view.View
import coil.api.load
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieBanner
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemHomeBannerBinding

class MovieBannerItem(
    val data : MovieBanner
) : ViewBindingItem<ItemHomeBannerBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_home_banner
    }

    override fun initBinding(itemView: View): ItemHomeBannerBinding {
        return ItemHomeBannerBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemHomeBannerBinding, position: Int) {
        viewBinding.imgScreen.load(data.imageUrl)
    }
}