package com.film.bazar.home_ui.detail.manager.invtinfo

import android.view.View
import coil.api.load
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.coreui.helper.LINEAR
import com.film.bazar.home_domain.InvestmentInfo
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemInvestmentInfoBinding

class InvestmentInfoItem(
    val data : InvestmentInfo
) : ViewBindingItem<ItemInvestmentInfoBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_investment_info
    }

    override fun initBinding(itemView: View): ItemInvestmentInfoBinding {
        return ItemInvestmentInfoBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemInvestmentInfoBinding, position: Int) {
        viewBinding.apply {
            root.tag = LINEAR
            ivIcon.load(data.imageUrl)
            tvLabel.text = data.label
            tvSubLabel.text = data.subLabel
        }
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }
}