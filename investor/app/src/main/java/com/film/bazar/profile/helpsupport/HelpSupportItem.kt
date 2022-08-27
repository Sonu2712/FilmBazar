package com.film.bazar.profile.helpsupport

import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemHelpSupportBinding
import com.film.bazar.domain.drawermenu.profile.HelpSupportQuestions
import com.film.bazar.profile.ProfileUiEvent
import io.reactivex.rxjava3.subjects.PublishSubject

class HelpSupportItem(
    val data: HelpSupportQuestions,
    val uiEvent: PublishSubject<ProfileUiEvent>
) : ViewBindingItem<ItemHelpSupportBinding>(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.item_help_support
    }

    override fun initBinding(itemView: View): ItemHelpSupportBinding {
        return ItemHelpSupportBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemHelpSupportBinding, position: Int) {
        viewBinding.apply {
            tvLabel.text = data.questionLabel
            root.setOnClickListener(this@HelpSupportItem)
        }
    }

    override fun onClick(v: View?) {
        uiEvent.onNext(ProfileUiEvent.OnItemClicked(data.questionId, data.questionLabel))
    }

    override fun unbind(viewHolder: VBViewHolder<ItemHelpSupportBinding>) {
        viewHolder.binding.root.setOnClickListener(null)
        super.unbind(viewHolder)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HelpSupportItem

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