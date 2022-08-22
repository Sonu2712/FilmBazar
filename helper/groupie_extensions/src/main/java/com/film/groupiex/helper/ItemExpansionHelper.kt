package com.film.groupiex.helper

import com.film.groupiex.helper.ItemExpansionHelper.Item

class ItemExpansionHelper<T : Item> : ItemExpansionListener {
  internal val itemList: MutableList<T> = mutableListOf()
  var singleItemAtOnce: Boolean = false

  fun setItems(items: List<T>) {
    itemList.clear()
    itemList.addAll(items)
  }

  fun itemList(): List<T> {
    return itemList.toList()
  }

  fun add(item: T) {
    itemList.add(item)
  }

  fun remove(item: T) {
    itemList.remove(item)
  }

  override fun onExpansionToggled(item: Item) {
    if (singleItemAtOnce && item.isExpanded) {
      for (t in itemList) {
        if (t.isExpanded && t != item) {
          t.collapse()
        }
      }
    }
  }

  interface Item {
    val isExpanded: Boolean
    fun expand()
    fun collapse()
    fun toggleExpanded()
  }

}

interface ItemExpansionListener {
  fun onExpansionToggled(item: Item)
}