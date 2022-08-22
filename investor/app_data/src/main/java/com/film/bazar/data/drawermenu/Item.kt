package com.film.bazar.data.drawermenu

data class Item(
    val icon: String,
    val identifier: String,
    val isEnabled: Boolean,
    val isExpandable: Boolean,
    val menuId: String,
    val style: String,
    val subMenuId: String,
    val title: String,
    val titleGu: String,
    val titleHi: String,
    val isDynamic:Boolean
)