package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieCastCrew
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastCrewBinding

class CastCrewItem(
    val data : MovieCastCrew
) : ViewBindingItem<ItemCastCrewBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_cast_crew
    }

    override fun initBinding(itemView: View): ItemCastCrewBinding {
        return ItemCastCrewBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCastCrewBinding, position: Int) {
        viewBinding.apply { 
            ivThumbnail.load(data.imageUrl)
            tvName.text = data.fName
            tvTitle.isVisible = data.lName.isNotEmpty()
            tvTitle.text = data.lName
            tvPosition.isVisible = data.position.isNotEmpty()
            tvPosition.text = data.position
        }
    }



    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CastCrewItem

        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    override fun getId(): Long {
        return data.hashCode().toLong()
    }
}