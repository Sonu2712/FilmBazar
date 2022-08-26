package com.film.bazar.home_ui.detail.manager.titlesubtitlepair

import android.view.View
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.TitleSubTitle
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemTitleSubtitleBinding

class TitleSubtitlePairItem(
    val data : TitleSubTitle
) : ViewBindingItem<ItemTitleSubtitleBinding>(){

    override fun getLayout(): Int {
        return R.layout.item_title_subtitle
    }

    override fun initBinding(itemView: View): ItemTitleSubtitleBinding {
        return ItemTitleSubtitleBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemTitleSubtitleBinding, position: Int) {
        viewBinding.apply {
            tvMajorInvtInfo.text = data.invtMsg1
            tvMajorInvtInfoValue.text = data.invtMsg2
        }
    }
}