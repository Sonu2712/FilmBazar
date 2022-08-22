package com.film.bazar.coreui.appcoreui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.ArrayList

class ViewStatePager(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
  private val mFragmentList = ArrayList<Fragment>()
  private val mFragmentTitleList = ArrayList<String>()

  val fragmentList: List<Fragment>
    get() = mFragmentList

  fun addFragment(fragment: Fragment, title: String) {
    mFragmentList.add(fragment)
    mFragmentTitleList.add(title)
  }

  fun addFragment(pos: Int, fragment: Fragment, title: String) {
    mFragmentList.add(pos, fragment)
    mFragmentTitleList.add(pos, title)
  }

  fun clear() {
    mFragmentList.clear()
    mFragmentTitleList.clear()
  }

  override fun getItemPosition(item: Any): Int {
    val index = mFragmentList.indexOf(item)
    return if (index == -1) {
      PagerAdapter.POSITION_NONE
    } else {
      index
    }
  }

  fun removeItem(pos: Int) {
    if (mFragmentList.size > pos) {
      mFragmentList.removeAt(pos)
    }
    if (mFragmentTitleList.size > pos) {
      mFragmentTitleList.removeAt(pos)
    }
    notifyDataSetChanged()
  }

  override fun getItem(position: Int): Fragment {
    return mFragmentList[position]
  }

  override fun getCount(): Int {
    return mFragmentList.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mFragmentTitleList[position]
  }
}
