package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import androidx.core.view.isVisible
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastCrewHeaderTitleBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import io.reactivex.rxjava3.subjects.PublishSubject

class CastCrewHeaderTitleItem : ViewBindingItem<ItemCastCrewHeaderTitleBinding>(),
    ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup
    private var binding: ItemCastCrewHeaderTitleBinding? = null
    override fun getLayout(): Int {
        return R.layout.item_cast_crew_header_title
    }

    override fun initBinding(itemView: View): ItemCastCrewHeaderTitleBinding {
        return ItemCastCrewHeaderTitleBinding.bind(itemView)
    }

    private val isExpanded: Boolean
        get() = expandableGroup.isExpanded

    override fun bind(viewBinding: ItemCastCrewHeaderTitleBinding, position: Int) {
        this.binding = viewBinding
        val root = viewBinding.root
        root.setOnClickListener {
            expandableGroup.onToggleExpanded()
            setIcon()
        }
        setIcon()
    }

    private fun setIcon() {
        val correctIcon: Int = if (isExpanded)
            R.drawable.ic_arrow_up else R.drawable.ic_spinner_arrow_down_dark
        // binding?.view?.isVisible = !isExpanded
        binding?.imageExp?.let {
            it.visibility = View.VISIBLE
            it.setImageResource(correctIcon)
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun unbind(viewHolder: VBViewHolder<ItemCastCrewHeaderTitleBinding>) {
        viewHolder.binding.root.setOnClickListener(null)
        super.unbind(viewHolder)
    }

}