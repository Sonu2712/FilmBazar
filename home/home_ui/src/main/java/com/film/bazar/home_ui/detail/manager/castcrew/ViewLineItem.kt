package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemViewLineBinding

class ViewLineItem : ViewBindingItem<ItemViewLineBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_view_line
    }

    override fun initBinding(itemView: View): ItemViewLineBinding {
        return ItemViewLineBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemViewLineBinding, position: Int) {
    }
}