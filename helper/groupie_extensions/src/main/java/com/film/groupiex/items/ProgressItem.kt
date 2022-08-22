package com.film.groupiex.items

import com.film.groupiex.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class ProgressItem : Item<GroupieViewHolder>() {

  override fun getLayout(): Int {
    return R.layout.item_progress_bar
  }

  override fun bind(viewHolder: GroupieViewHolder, position: Int) {}

  override fun isClickable(): Boolean {
    return false
  }
}
