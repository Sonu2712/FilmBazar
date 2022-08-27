package com.film.bazar.profile.helpsupport.paymentrefund

import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemPaymentRefundExpandbleAnswerBinding

class PaymentRefundAnswerItem(
    val value : String
) : ViewBindingItem<ItemPaymentRefundExpandbleAnswerBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_payment_refund_expandble_answer
    }

    override fun initBinding(itemView: View): ItemPaymentRefundExpandbleAnswerBinding {
        return ItemPaymentRefundExpandbleAnswerBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemPaymentRefundExpandbleAnswerBinding, position: Int) {
        viewBinding.tvAnswer.text = value
    }
}