package com.film.bazar.home_ui.movietab

import android.view.View
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.coreui.helper.OTHER
import com.film.bazar.coreui.tabs.SimpleTabListener
import com.film.bazar.home_domain.MovieTab
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemHeaderTabBinding
import com.google.android.material.tabs.TabLayout
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieTabItem(
    val tabs: List<MovieTab>,
    val tabListener: SimpleTabListener,
    val uiEvent: PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemHeaderTabBinding>() {

    var selectedPosition = 0
    override fun getLayout(): Int {
        return R.layout.item_header_tab
    }

    override fun initBinding(itemView: View): ItemHeaderTabBinding {
        return ItemHeaderTabBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemHeaderTabBinding, position: Int) {
        viewBinding.root.tag = OTHER
        viewBinding.tabLayout.apply {
            val tabList = tabs.map { it }
            if (tag != tabList) {
                tabGravity = TabLayout.GRAVITY_FILL
                tabMode = TabLayout.MODE_FIXED
                removeAllTabs()
                tabs.forEach {
                    val tab = newTab()
                        .setText(it.toString())
                        .setTag(it)
                    addTab(tab)
                }
                tag = tabList
            }
        }
        viewBinding.tabLayout.getTabAt(selectedPosition)?.select()
        viewBinding.tabLayout.addOnTabSelectedListener(tabListener)
        viewBinding.ivFilter.setOnClickListener {
            uiEvent.onNext(HomeUiEvent.OpenSortFilterBottomSheet)
        }
    }

    fun updatePosition(tabname: String) {
        if (tabname == MovieTab.PAST_PROJECT.toString()) {
            selectedPosition = 1
        } else {
            selectedPosition = 0
        }
    }

    override fun unbind(holder: VBViewHolder<ItemHeaderTabBinding>) {
        holder.binding.tabLayout.clearOnTabSelectedListeners()
        super.unbind(holder)
    }

    override fun isRecyclable(): Boolean {
        return false
    }
}
