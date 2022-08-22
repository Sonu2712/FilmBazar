package com.film.bazar.drawermenu.item

import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemChildMenuBinding
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.domain.drawermenu.ChildMenu

class ChildMenuItem(
  private val childMenu: ChildMenu
) : ViewBindingItem<ItemChildMenuBinding>(), SelectableMenu {
  private var isSelected: Boolean = false

  override fun getLayout(): Int {
    return R.layout.item_child_menu
  }

  override fun initBinding(itemView: View): ItemChildMenuBinding {
    return ItemChildMenuBinding.bind(itemView)
  }

  override fun setSelected(selected: Boolean) {
    if (isSelected != selected) {
      isSelected = selected
      notifyChanged()
    }
  }

  override fun getAppMenu(): AppMenu {
    return childMenu
  }

  override fun bind(viewBinding: ItemChildMenuBinding, position: Int) {
    val txtRoot = viewBinding.txtChildMenu
    txtRoot.isEnabled = childMenu.enabled
    if (childMenu.label.isNotEmpty()) {
        txtRoot.text = childMenu.label
    } else {
      txtRoot.text = childMenu.labelStr
    }
  }

  override fun getId(): Long {
    return childMenu.label.toLong()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ChildMenuItem

    if (childMenu != other.childMenu) return false
    if (isSelected != other.isSelected) return false

    return true
  }

  override fun hashCode(): Int {
    var result = childMenu.hashCode()
    result = 31 * result + isSelected.hashCode()
    return result
  }
}