package com.film.bazar.coreui.navigatorlib

import androidx.fragment.app.Fragment

interface NavigationCallback {
  fun onPageOpened(fragment: Fragment, addedToStack: Boolean)

  fun onBackNavigation()
}