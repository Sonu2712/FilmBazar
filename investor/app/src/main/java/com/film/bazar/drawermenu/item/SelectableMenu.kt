package com.film.bazar.drawermenu.item

import com.film.bazar.domain.drawermenu.AppMenu

interface SelectableMenu {
  fun setSelected(selected: Boolean)
  fun getAppMenu(): AppMenu
}