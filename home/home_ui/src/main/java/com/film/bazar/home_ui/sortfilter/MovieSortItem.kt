package com.film.bazar.home_ui.sortfilter

import android.view.View
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieSortKeyValue
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemMoviewSortBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieSortItem(
    val data : MovieSortKeyValue,
    val uiEvent: PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemMoviewSortBinding>(), View.OnClickListener{
    override fun getLayout(): Int {
        return R.layout.item_moview_sort
    }

    override fun initBinding(itemView: View): ItemMoviewSortBinding {
        return ItemMoviewSortBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemMoviewSortBinding, position: Int) {
        viewBinding.rbLael.text = data.label
        viewBinding.root.setOnClickListener(this)
    }

    override fun unbind(viewHolder: VBViewHolder<ItemMoviewSortBinding>) {
        viewHolder.binding.root.setOnClickListener(null)
        super.unbind(viewHolder)
    }

    override fun onClick(v: View?) {
        uiEvent.onNext(HomeUiEvent.SortItemClicked(data))
    }

    override fun getId(): Long {
        return data.hashCode().toLong()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieSortItem

        if (data != other.data) return false
        if (uiEvent != other.uiEvent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + uiEvent.hashCode()
        return result
    }
}