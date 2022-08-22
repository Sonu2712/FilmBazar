package com.film.bazar.coreui.appcoreui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.appcoreui.adapters.PagerFragmentProvider
import com.film.bazar.coreui.appcoreui.adapters.ViewPagerAdapter
import com.film.bazar.coreui.appcoreui.views.CustomViewPager
import timber.log.Timber
import javax.inject.Inject

abstract class PagerBaseFragment : BaseFragment(), ViewPagerAdapter.Helper {
  @Inject
  lateinit var sharedPreferences: SharedPreferences

  @JvmField
  protected val pageChangeListener: ViewPager.OnPageChangeListener =
      object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
          onPageSelection(position)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
      }

  private var mPager: CustomViewPager? = null
  private var mIndicator: TabLayout? = null

  override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val (customViewPager, tabLayout) = getPagerViews()
    mPager = customViewPager
    mIndicator = tabLayout
  }

  protected abstract fun getPagerViews(): Pair<CustomViewPager, TabLayout>

  @CallSuper
  open fun onPageSelection(position: Int) {
    dispatchSendServiceRequest(position)
  }

  override fun onFragmentReopened(args: Bundle) {
    var newFragSource = -1
    var openFragSource = -1
    val currentArgs = arguments
    if (currentArgs != null) {
      newFragSource = args.getInt(ARG_TARGET_ITEM_POSITION, -1)
      openFragSource = currentArgs.getInt(ARG_TARGET_ITEM_POSITION, -1)
    }
    if (newFragSource == openFragSource) {
      Timber.d("Already on the same page")
    } else {
      mPager?.currentItem = newFragSource
    }
  }

  abstract override fun getItem(pos: Int): Fragment

  abstract fun getAdapter(): com.film.bazar.coreui.appcoreui.adapters.PagerFragmentProvider?

  override fun onFragmentResume() {
    super.onFragmentResume()
    mPager?.let {
      dispatchOnFragmentResume(it)
      dispatchSendServiceRequest(it)
    }
  }

  fun dispatchOnFragmentResume(mPager: ViewPager) {
    getAdapter()?.getFragmentAt(mPager.currentItem)
        ?.onFragmentResume()
  }

  @CallSuper override fun onFragmentResult(data: Bundle) {
    mPager?.let {
      getAdapter()?.getFragmentAt(it.currentItem)
          ?.onFragmentResult(data)
    }
  }

  final override fun sendServiceRequest() {
    dataActionSubject.onNext(DataAction.Retry)
    mPager?.let {
      dispatchSendServiceRequest(it)
    }
  }

  fun dispatchSendServiceRequest(mPager: ViewPager) {
    dispatchSendServiceRequest(mPager.currentItem)
  }

  open fun dispatchSendServiceRequest(pos: Int) {
    val fragmentAt = getAdapter()?.getFragmentAt(pos)
    fragmentAt?.sendServiceRequest()
  }

  override fun onBackPressed(): Boolean {
    return mPager?.let {
      getAdapter()?.getFragmentAt(it.currentItem)?.onBackPressed()
    } ?: super.onBackPressed();
  }

  @CallSuper
  override fun onDestroyView() {
    mPager?.let {
      it.clearOnPageChangeListeners()
      it.adapter = null
    }
    mIndicator?.setupWithViewPager(null)
    super.onDestroyView()
  }

  companion object {
    const val ARG_TARGET_ITEM_POSITION = "pagerItemPosition"
  }
}