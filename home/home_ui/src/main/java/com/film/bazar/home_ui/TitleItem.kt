package com.film.bazar.home_ui

import android.view.View
import androidx.core.view.isVisible
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.databinding.ItemHeaderTitleBinding

class TitleItem(
    val title : String,
    val isViewAll : Boolean
) : ViewBindingItem<ItemHeaderTitleBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_header_title
    }

    override fun initBinding(itemView: View): ItemHeaderTitleBinding {
        return ItemHeaderTitleBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemHeaderTitleBinding, position: Int) {
        viewBinding.tvTitle.text = title
        viewBinding.tvViewAll.isVisible = isViewAll
    }
}