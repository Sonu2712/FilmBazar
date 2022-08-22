package com.film.bazar.helper

import com.film.bazar.coreui.navigatorlib.NavigationEntry

data class CustomNavigationEntry(
  @JvmField val pageId: String,
  @JvmField val data: Any? = null
) : NavigationEntry()


object OpenAccountNavigationEntry : NavigationEntry()
data class OpenAccountDeepLinkNavigationEntry(val deepLinkString:String) : NavigationEntry()