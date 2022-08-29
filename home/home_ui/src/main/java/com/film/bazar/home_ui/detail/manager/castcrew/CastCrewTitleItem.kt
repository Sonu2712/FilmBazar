package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastTitleBinding

class CastCrewTitleItem(
    val title : String
) : ViewBindingItem<ItemCastTitleBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_cast_title
    }

    override fun initBinding(itemView: View): ItemCastTitleBinding {
        return ItemCastTitleBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCastTitleBinding, position: Int) {
        viewBinding.tvName.text = title
    }
}