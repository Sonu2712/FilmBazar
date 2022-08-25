package com.film.bazar.home_ui.movietab

import android.view.View
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemFilterProjectBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieFilterItem(
    val homeUiEvent : PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemFilterProjectBinding>() {
    override fun getLayout(): Int {
        return R.layout.item_filter_project
    }

    override fun initBinding(itemView: View): ItemFilterProjectBinding {
        return ItemFilterProjectBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemFilterProjectBinding, position: Int) {
        viewBinding.ivFilter.setOnClickListener {
            homeUiEvent.onNext(HomeUiEvent.OpenSortFilterBottomSheet)
        }
    }

    override fun unbind(viewHolder: VBViewHolder<ItemFilterProjectBinding>) {
        viewHolder.binding.ivFilter.setOnClickListener(null)
        super.unbind(viewHolder)
    }
}