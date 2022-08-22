package com.film.bazar.coreui.appcoreui.adapters

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.film.bazar.coreui.R
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import java.util.*


class ViewPagerAdapter2 @JvmOverloads constructor(
    fragment: Fragment,
    private val mContext: Context,
    val helper: Helper,
    private val icons: IntArray? = null
) : FragmentStateAdapter(fragment), com.film.bazar.coreui.appcoreui.adapters.PagerFragmentProvider {
    private var mFragmentTitleList: List<String> = ArrayList()
    private val registeredFragments = SparseArray<Fragment>()

    fun setFragmentTitles(mFragmentTitleList: List<String>) {
        this.mFragmentTitleList = mFragmentTitleList
    }

    fun setFragmentTitles(@ArrayRes mFragmentTitleList: Int) {
        this.mFragmentTitleList = mContext.resources.getStringArray(mFragmentTitleList).asList()
    }

    fun getPageTitle(position: Int): CharSequence? {
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

    override fun getItemCount(): Int {
        return mFragmentTitleList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = helper.getItem(position)
        registeredFragments.put(position, fragment)
        return fragment
    }

    private fun getRegisteredFragment(position: Int): Fragment? {
        return registeredFragments.get(position)
    }

    interface Helper {
        fun getItem(pos: Int): Fragment
    }
}