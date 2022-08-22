package com.film.bazar.coreui.navigatorlib

import android.os.Bundle

interface NavigationElement {
  /**
   * It is called when a user presses back button on the activity.
   *
   * @return true if you want to consume the event, else false for the activity's
   * onBackPressed() to be processed.
   */
  fun onBackPressed(): Boolean

  fun isBackPressEnabled(): Boolean

  fun onFragmentReopened(args: Bundle)

  fun setFragmentResult(result: Bundle)

  fun onFragmentResume()

  fun onFragmentPause()
}
