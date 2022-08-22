package com.film.bazar.coreui.appcoreui.adapters

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface TabIndicator {
  val label: Int
  val icon: Int
}

data class SimpleTabIndicator(
    @StringRes override val label: Int,
    @DrawableRes override val icon: Int
) : com.film.bazar.coreui.appcoreui.adapters.TabIndicator