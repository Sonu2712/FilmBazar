package com.film.bazar.drawermenu.item

import android.os.Build
import android.view.View
import coil.api.load
import com.film.bazar.R
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemBaseMenuBinding
import com.film.bazar.domain.drawermenu.BaseMenu
import com.film.bazar.drawermenu.data.getIcon
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.OnItemClickListener

class BaseMenuExpandableItem(
    val baseMenu: BaseMenu,
    val itemExpansionListener: OnItemClickListener
) : ViewBindingItem<ItemBaseMenuBinding>(),
    ExpandableItem {
    internal lateinit var expandableGroup: ExpandableGroup
    private var binding: ItemBaseMenuBinding? = null

    override fun bind(
        viewBinding: ItemBaseMenuBinding,
        position: Int
    ) {
        binding = viewBinding

        viewBinding.root.let {
            it.isEnabled = baseMenu.enabled
            it.setOnClickListener { v ->
                expandableGroup.onToggleExpanded()
                setIcon()
                itemExpansionListener.onItemClick(this, v)
            }
        }

        viewBinding.txtBaseMenu.isEnabled = baseMenu.enabled
        viewBinding.txtBaseMenu.text = baseMenu.label
        val icon = baseMenu.pageId.getIcon()
        if (baseMenu.icon.isNotEmpty() || icon > 0) {
            viewBinding.imgGroupImage.apply {
                when {
                    baseMenu.icon.isNotEmpty() -> load(baseMenu.icon)
                    else -> load(baseMenu.pageId.getIcon())
                }
            }
            viewBinding.imgGroupImage.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewBinding.txtBaseMenu.setTextAppearance(R.style.Main_Menu)
            }
        } else {
            viewBinding.imgGroupImage.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewBinding.txtBaseMenu.setTextAppearance(R.style.Widget_MoslTheme_DecorativeText)
            }
        }
        setIcon()
    }

    override fun getLayout(): Int {
        return R.layout.item_base_menu
    }


    override fun initBinding(itemView: View): ItemBaseMenuBinding {
        return ItemBaseMenuBinding.bind(itemView)
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    val isExpanded: Boolean
        get() = expandableGroup.isExpanded

    fun collapse() {
        if (isExpanded) {
            expandableGroup.onToggleExpanded()
            setIcon()
        }
    }

    fun expand() {
        if (!isExpanded) {
            expandableGroup.onToggleExpanded()
            setIcon()
        }
    }

    internal fun setIcon() {
        val correctIcon: Int = if (isExpanded) {
            R.drawable.app_ic_expand_menu } else {
            R.drawable.app_ic_collapse_menu }
        binding?.txtBaseMenu?.setCompoundDrawablesWithIntrinsicBounds(
            0, 0, correctIcon,
            0
        )
    }
}