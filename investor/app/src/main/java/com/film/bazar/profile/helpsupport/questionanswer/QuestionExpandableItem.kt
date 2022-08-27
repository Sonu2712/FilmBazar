package com.film.bazar.profile.helpsupport.questionanswer

import android.view.View
import androidx.core.view.isVisible
import com.film.bazar.R
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemPaymentRefundExpandbleBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem

class QuestionExpandableItem(
    val label : String
) : ViewBindingItem<ItemPaymentRefundExpandbleBinding>(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    private var binding : ItemPaymentRefundExpandbleBinding? = null
    override fun getLayout(): Int {
        return R.layout.item_payment_refund_expandble
    }

    override fun initBinding(itemView: View): ItemPaymentRefundExpandbleBinding {
        return ItemPaymentRefundExpandbleBinding.bind(itemView)
    }

    private val isExpanded: Boolean
        get() = expandableGroup.isExpanded

    override fun bind(viewBinding: ItemPaymentRefundExpandbleBinding, position: Int) {
        binding = viewBinding
        val root = viewBinding.root
        root.setOnClickListener {
            expandableGroup.onToggleExpanded()
            setIcon()
        }
        setIcon()
        viewBinding.tvLabel.text = label
    }

    private fun setIcon() {
        val correctIcon: Int = if (isExpanded)
            R.drawable.ic_arrow_up else R.drawable.ic_spinner_arrow_down_dark
        binding?.view?.isVisible = !isExpanded
        binding?.imageExp?.let {
            it.visibility = View.VISIBLE
            it.setImageResource(correctIcon)
        }
    }
    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun unbind(viewHolder: VBViewHolder<ItemPaymentRefundExpandbleBinding>) {
        viewHolder.binding.root.setOnClickListener(null)
        super.unbind(viewHolder)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionExpandableItem

        if (label != other.label) return false
        if (expandableGroup != other.expandableGroup) return false
        if (binding != other.binding) return false
        if (isExpanded != other.isExpanded) return false

        return true
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + expandableGroup.hashCode()
        result = 31 * result + (binding?.hashCode() ?: 0)
        return result
    }

    override fun getId(): Long {
        return label.hashCode().toLong()
    }
}