package com.film.bazar.coreui.appcoreui.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class CriticalButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

  /*
   * (non-Javadoc)
   * @see android.view.View#performClick()
   * Overridden to disable button on click permanently
   */
  override fun performClick(): Boolean {
    isEnabled = false
    return super.performClick()
  }
}
