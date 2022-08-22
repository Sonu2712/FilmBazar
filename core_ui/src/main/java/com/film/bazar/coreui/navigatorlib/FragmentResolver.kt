package com.film.bazar.coreui.navigatorlib

import androidx.fragment.app.Fragment

interface FragmentResolver {
  fun resolve(pageId: String): Fragment?
}