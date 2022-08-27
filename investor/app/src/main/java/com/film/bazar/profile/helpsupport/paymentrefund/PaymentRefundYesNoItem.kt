package com.film.bazar.profile.helpsupport.paymentrefund

import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemPaymentRefundYesNoBinding
import com.film.bazar.domain.drawermenu.profile.AnswerValue
import com.film.bazar.domain.drawermenu.profile.AnswerYesNo

class PaymentRefundYesNoItem(
    val data: AnswerValue
) : ViewBindingItem<ItemPaymentRefundYesNoBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_payment_refund_yes_no
    }

    override fun initBinding(itemView: View): ItemPaymentRefundYesNoBinding {
        return ItemPaymentRefundYesNoBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemPaymentRefundYesNoBinding, position: Int) {
        viewBinding.rbYesNo.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbYes -> {
                    data.selected = AnswerYesNo.YES
                }
                R.id.rbNo -> {
                    data.selected = AnswerYesNo.NO
                }
                else -> {
                    data.selected = AnswerYesNo.NOTHING
                }
            }
        }
    }

    override fun unbind(viewHolder: VBViewHolder<ItemPaymentRefundYesNoBinding>) {
        viewHolder.binding.rbYesNo.setOnCheckedChangeListener(null)
        super.unbind(viewHolder)
    }
}