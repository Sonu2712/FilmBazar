package com.film.bazar.coreui.appcoreui.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(
    context: Context,
    attrs: AttributeSet?
) : ViewPager(context, attrs) {
  private var pagingEnabled: Boolean = true

  fun setPagingEnabled(enabled: Boolean) {
    this.pagingEnabled = enabled
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    return this.pagingEnabled && super.onTouchEvent(event)
  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    return this.pagingEnabled && super.onInterceptTouchEvent(event)
  }
}
