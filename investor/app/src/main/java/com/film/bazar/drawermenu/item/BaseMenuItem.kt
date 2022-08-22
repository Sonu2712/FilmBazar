package com.film.bazar.drawermenu.item

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import coil.api.load
import com.film.bazar.R
import com.film.bazar.bottombar.getIcon
import com.film.bazar.coreui.groupie.VBViewHolder
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemBaseMenuBinding
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.domain.drawermenu.BaseMenu

class BaseMenuItem(
    private val baseMenu: BaseMenu
) : ViewBindingItem<ItemBaseMenuBinding>(), SelectableMenu {
    private var isSelected: Boolean = false
    override fun getLayout(): Int {
        return R.layout.item_base_menu
    }

    override fun initBinding(itemView: View): ItemBaseMenuBinding {
        return ItemBaseMenuBinding.bind(itemView)
    }

    override fun setSelected(selected: Boolean) {
        if (this.isSelected != selected) {
            this.isSelected = selected
            notifyChanged()
        }
    }

    override fun getAppMenu(): AppMenu {
        return baseMenu
    }

    override fun bind(viewBinding: ItemBaseMenuBinding, position: Int) {
        val txtRoot = viewBinding.txtBaseMenu
        txtRoot.text = baseMenu.label
        viewBinding.txtBaseMenu.isEnabled = baseMenu.enabled
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
                txtRoot.setTextAppearance(R.style.Main_Menu)
            }
        } else {
            viewBinding.imgGroupImage.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               // txtRoot.setTextAppearance(R.style.Widget_MoslTheme_DecorativeText)
            }
        }
        txtRoot.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    private fun ItemBaseMenuBinding.toggleSubmenuAndNew(isShown : Boolean){
        txtBaseSubMenu.isVisible = isShown
        tvNew.isVisible = isShown
    }

    override fun getId(): Long {
        return baseMenu.hashCode().toLong()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseMenuItem

        if (baseMenu != other.baseMenu) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = baseMenu.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }

    override fun unbind(viewHolder: VBViewHolder<ItemBaseMenuBinding>) {
        super.unbind(viewHolder)
    }


    private fun TextView.setBlinkingText(label: String) {
        val minAmountMessage=label.split("|")[1]
        val title = label.trim().replace("|","")
        val startIndex=title.indexOf(minAmountMessage)
        if(startIndex!=-1){
        }
        else text=label
    }
}