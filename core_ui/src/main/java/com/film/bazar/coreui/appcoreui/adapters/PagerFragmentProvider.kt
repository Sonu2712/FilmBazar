package com.film.bazar.coreui.appcoreui.adapters

import com.film.bazar.coreui.appcoreui.base.BaseFragment

interface PagerFragmentProvider {
  fun getFragmentAt(position: Int): BaseFragment?
}