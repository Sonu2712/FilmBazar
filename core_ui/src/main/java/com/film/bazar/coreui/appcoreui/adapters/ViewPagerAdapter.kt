package com.film.bazar.coreui.appcoreui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.film.bazar.coreui.R
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import java.util.ArrayList

class ViewPagerAdapter @JvmOverloads constructor(
    fm: FragmentManager,
    private val mContext: Context,
    private val helper: Helper,
    private val icons: IntArray? = null
) : com.film.bazar.coreui.appcoreui.adapters.SmartFragmentPagerAdapter(fm),
  com.film.bazar.coreui.appcoreui.adapters.PagerFragmentProvider {
  private var mFragmentTitleList: List<String> = ArrayList()

  fun setFragmentTitles(mFragmentTitleList: List<String>) {
    this.mFragmentTitleList = mFragmentTitleList
  }

  fun setFragmentTitles(@ArrayRes mFragmentTitleList: Int) {
    this.mFragmentTitleList = mContext.resources.getStringArray(mFragmentTitleList).asList()
  }

  /**
   * This method should never be called by Hand Written Code
   * instead use getFragmentAt(position)
   */
  override fun getItem(position: Int): Fragment {
    return helper.getItem(position)
  }

  override fun getCount(): Int {
    return mFragmentTitleList.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mFragmentTitleList[position]
  }

  fun getTabView(position: Int): View {
    if (icons == null) {
      throw IllegalStateException("Please use the constructor with Context & Icons")
    }
    val tv = LayoutInflater.from(mContext).inflate(R.layout.view_custom_tab, null) as TextView
    tv.text = getPageTitle(position)
    tv.setCompoundDrawablesWithIntrinsicBounds(0, icons[position], 0, 0)
    return tv
  }

  override fun getFragmentAt(position: Int): BaseFragment? {
    val fragment = getRegisteredFragment(position)
    return if (fragment is BaseFragment) {
      fragment
    } else null
  }

  interface Helper {
    fun getItem(pos: Int): Fragment
  }
}
