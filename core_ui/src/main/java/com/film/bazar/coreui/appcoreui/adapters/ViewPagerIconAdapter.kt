package com.film.bazar.coreui.appcoreui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.film.bazar.coreui.appcoreui.adapters.ViewPagerAdapter.Helper
import com.film.bazar.coreui.appcoreui.base.BaseFragment

class ViewPagerIconAdapter(
    fm: FragmentManager,
    private val mContext: Context,
    private val helper: Helper,
    private var tabs: List<com.film.bazar.coreui.appcoreui.adapters.TabIndicator> = emptyList()
) : com.film.bazar.coreui.appcoreui.adapters.SmartFragmentPagerAdapter(fm),
  com.film.bazar.coreui.appcoreui.adapters.PagerFragmentProvider {

  fun setTabs(tabs: List<com.film.bazar.coreui.appcoreui.adapters.TabIndicator>) {
    this.tabs = tabs
  }

  /**
   * This method should never be called by Hand Written Code
   * instead use getFragmentAt(position)
   */
  override fun getItem(position: Int): Fragment {
    return helper.getItem(position)
  }

  override fun getCount(): Int {
    return tabs.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mContext.getString(tabs[position].label)
  }

  override fun getFragmentAt(position: Int): BaseFragment? {
    val fragment = getRegisteredFragment(position)
    return if (fragment is BaseFragment) {
      fragment
    } else null
  }
}