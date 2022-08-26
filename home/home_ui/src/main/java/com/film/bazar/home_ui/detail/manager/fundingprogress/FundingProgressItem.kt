package com.film.bazar.home_ui.detail.manager.fundingprogress

import android.view.View
import com.film.app.core.utils.toRoundedRupees
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieFund
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemFundingProgressBinding

class FundingProgressItem(
    val data : MovieFund
) : ViewBindingItem<ItemFundingProgressBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_funding_progress
    }

    override fun initBinding(itemView: View): ItemFundingProgressBinding {
        return ItemFundingProgressBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemFundingProgressBinding, position: Int) {
        viewBinding.apply {
            tvDays.text = "${data.daysLeft} days left"
            tvTargetValue.text = data.targetAmount.toRoundedRupees()
            pbGrowth.progress = data.daysLeft
            tvTargetGoal.text = "of ${data.targetGoalAmount.toRoundedRupees()} goals"
        }
    }
}