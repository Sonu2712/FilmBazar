package com.film.bazar.coreui.appcoreui.base

import androidx.annotation.CallSuper
import com.film.bazar.coreui.navigatorlib.FragmentTransitionHelper
import javax.inject.Inject

abstract class BaseActivity : LeanBaseActivity() {
  @Inject
  lateinit var fragTransitionHelper: com.film.bazar.coreui.navigatorlib.FragmentTransitionHelper

  @CallSuper
  override fun onPostResume() {
    super.onPostResume()
    /*
         * Calling Transition Helper resume after activity has resumed.
         * http://stackoverflow.com/questions/16265733/failure-delivering-result-onactivityforresult
         */
    fragTransitionHelper.resume()
  }

  @CallSuper
  override fun onPause() {
    super.onPause()
    fragTransitionHelper.pause()
  }
}