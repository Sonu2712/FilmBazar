package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastCrewListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class CastCrewListItem(
    val innerAdapter: GroupAdapter<GroupieViewHolder>
) : ViewBindingItem<ItemCastCrewListBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_cast_crew_list
    }

    override fun initBinding(itemView: View): ItemCastCrewListBinding {
        return ItemCastCrewListBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCastCrewListBinding, position: Int) {
    }

    override fun createViewHolder(itemView: View): VBViewHolder<ItemCastCrewListBinding> {
        val viewHolder: VBViewHolder<ItemCastCrewListBinding> =
            super.createViewHolder(itemView)
        val context = viewHolder.root.context
        viewHolder.binding.rvCastCrew.apply {
            layoutManager = GridLayoutManager(
                context,
                3, RecyclerView.VERTICAL, false
            )
            adapter = innerAdapter
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }
        return viewHolder
    }

    fun update(items: List<CastCrewItem>) {
        innerAdapter.update(items)
    }
}