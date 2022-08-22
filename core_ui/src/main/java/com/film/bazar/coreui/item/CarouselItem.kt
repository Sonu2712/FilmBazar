package com.mosl.mobile.coreui.item

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.film.bazar.coreui.R
import com.film.bazar.coreui.databinding.ItemCarouselBinding
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.coreui.helper.CAROUSEL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class CarouselItem(
    private val groupAdapter: GroupAdapter<GroupieViewHolder>,
    private val onScrollListener: RecyclerView.OnScrollListener? = null,
    private val showPageIndicator: Boolean = false,
    private val showFooterText: Boolean = false,
    private val attachToRecyclerView: Boolean = true
) : ViewBindingItem<ItemCarouselBinding>() {
    val snapHelper = PagerSnapHelper()
    var viewBinding: ItemCarouselBinding? = null
    var defaultPosition: Int = -1

    fun update(items: List<Item<*>>) {
        groupAdapter.update(items)
    }

    fun getSnapPosition(): Int {
        val layoutManager =
            viewBinding?.rvCarousel?.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = snapHelper.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    fun scrollTo(position: Int) {
        viewBinding?.rvCarousel?.smoothScrollToPosition(position)
    }

    fun scrollToWithDelay(position: Int) {
        viewBinding?.rvCarousel?.postDelayed({
            viewBinding?.rvCarousel?.smoothScrollToPosition(position)
        }, 100)
    }

    override fun createViewHolder(itemView: View): VBViewHolder<ItemCarouselBinding> {
        val viewHolder = super.createViewHolder(itemView)
        viewHolder.binding.rvCarousel.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        if (attachToRecyclerView)
            snapHelper.attachToRecyclerView(viewHolder.binding.rvCarousel)
        return viewHolder
    }

    override fun getLayout(): Int {
        return R.layout.item_carousel
    }

    override fun initBinding(itemView: View): ItemCarouselBinding {
        return ItemCarouselBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCarouselBinding, position: Int) {
        this.viewBinding = viewBinding
        viewBinding.root.tag = CAROUSEL
        viewBinding.rvCarousel.adapter = groupAdapter
        if (showPageIndicator) {
            viewBinding.indicator.isVisible = showPageIndicator
            viewBinding.indicator.attachToRecyclerView(viewBinding.rvCarousel, snapHelper)
        } else {
            viewBinding.indicator.isVisible = showPageIndicator
        }
        viewBinding.tvFooter.isVisible = showFooterText
        onScrollListener?.let { viewBinding.rvCarousel.addOnScrollListener(it) }
        if (defaultPosition != -1) {
            scrollTo(defaultPosition)
        }
    }

    override fun isRecyclable(): Boolean {
        return false
    }
}
