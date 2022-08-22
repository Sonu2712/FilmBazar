package com.film.bazar.data.drawermenu

data class AllMenu(
    val icon: String?,
    val identifier: String,
    val isEnabled: Boolean,
    val isExpandable: Boolean,
    val items: List<Item>?,
    val menuId: String,
    val style: String,
    val title: String,
    val titleGu: String,
    val titleHi: String,
    val isDynamic: Boolean
)