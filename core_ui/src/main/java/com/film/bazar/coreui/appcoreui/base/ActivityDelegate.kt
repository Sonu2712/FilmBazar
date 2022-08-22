package com.film.bazar.coreui.appcoreui.base

import android.view.View.OnClickListener
import androidx.annotation.StringRes
import timber.log.Timber

interface ActivityDelegate {
  fun showOverflowMenu()
  fun hideOverflowMenu()
  fun setOverflowClickListener(listener: OnClickListener?)
  fun selectMenu(@StringRes label: Int)
}

fun ActivityDelegate.toggleOverflowMenu(show: Boolean) {
  if (show) {
    showOverflowMenu()
  } else {
    hideOverflowMenu()
  }
}

@JvmField
val EmptyActivityDelegate = object : ActivityDelegate {
  override fun showOverflowMenu() {
    Timber.d("showOverflowMenu")
  }

  override fun hideOverflowMenu() {
    Timber.d("hideOverflowMenu")
  }

  override fun setOverflowClickListener(listener: OnClickListener?) {
    Timber.d("setOverflowClickListener called")
  }

  override fun selectMenu(label: Int) {
    Timber.d("selectMenu called")
  }
}