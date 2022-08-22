@file:JvmName("TabUtils")
@file:Suppress("UNCHECKED_CAST")

package com.film.bazar.coreui.appcoreui.tab

import com.google.android.material.tabs.TabLayout

fun <T> TabLayout.setupTabs(tabList: List<T>, retainSelection: Boolean = true) {
  val currentSelection = if (retainSelection) {
    getTabAt(selectedTabPosition)?.tag as? T
  } else null
  setupTabsWithSelection(tabList, currentSelection)
}

fun <T> TabLayout.setTabSelection(data: T): Boolean {
  for (i in 0 until tabCount) {
    getTabAt(i)?.let {
      if (it.tag == data) {
        val selected = it.isSelected
        it.select()
        return !selected
      }
    }
  }
  return false
}

fun <T> TabLayout.setupTabsWithSelection(tabList: List<T>, defaultSelection: T? = null) {
  removeAllTabs()
  for (item in tabList) {
    val tab = newTab().apply {
      text = item.toString()
      tag = item
    }
    addTab(tab, false)
  }
  val indexOf = if (tabList.contains(defaultSelection)) {
    tabList.indexOf(defaultSelection)
  } else 0

  selectTab(getTabAt(indexOf), true)
}

fun <T> TabLayout.getSelectedTabItem(): T? {
  val tabPosition = selectedTabPosition
  return if (tabPosition == -1) {
    null
  } else getTabAt(tabPosition)?.tag as? T
}
