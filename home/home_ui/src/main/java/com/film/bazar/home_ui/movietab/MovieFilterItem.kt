package com.film.bazar.home_ui.movietab

import android.view.View
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemFilterProjectBinding

class MovieFilterItem : ViewBindingItem<ItemFilterProjectBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_filter_project
    }

    override fun initBinding(itemView: View): ItemFilterProjectBinding {
        return ItemFilterProjectBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemFilterProjectBinding, position: Int) {
    }
}