package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastCrewDirectorBinding

class CastCredDirecterTitleItem(
    val name : String
) : ViewBindingItem<ItemCastCrewDirectorBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_cast_crew_director
    }

    override fun initBinding(itemView: View): ItemCastCrewDirectorBinding {
        return ItemCastCrewDirectorBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCastCrewDirectorBinding, position: Int) {
        viewBinding.tvDirectorName.text = name
    }
}