package com.film.bazar.nav

import androidx.fragment.app.Fragment
import com.film.bazar.dashboard.DashboardFragment
import com.film.bazar.drawermenu.DrawerMenuFragment
import com.film.bazar.featurescope.FirstPlaygroundFragment
import com.film.bazar.featurescope.SecondPlaygroundFragment
import com.film.bazar.nav.DebugConstants

object DebugMenuGetter {
  @JvmStatic
  fun getFragment(@DebugConstants pageId: String): Fragment? {
    return when (pageId) {
      DebugConstants.NAVIGATE_TO_PLAYGROUND_01 -> FirstPlaygroundFragment()
      DebugConstants.NAVIGATE_TO_PLAYGROUND_02 -> SecondPlaygroundFragment()
      else -> null
    }
  }

  @JvmStatic
  fun isHandled(@DebugConstants pageId: String): Boolean {
    return when (pageId) {
      DebugConstants.NAVIGATE_TO_PLAYGROUND_01 -> true
      DebugConstants.NAVIGATE_TO_PLAYGROUND_02 -> true
      else -> false
    }
  }
}
