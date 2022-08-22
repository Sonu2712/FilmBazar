@file:JvmName("Keyboards")

package com.film.app.core.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

@JvmOverloads
fun hideKeyBoard(
    view: View,
    flags: Int = 0
) {
  val inputManager = getInputManager(view.context)
  if (inputManager.isAcceptingText) {
    inputManager.hideSoftInputFromWindow(view.windowToken, flags)
  }
}

@JvmOverloads
fun showKeyBoard(
    view: View,
    flags: Int = 0
) {
  getInputManager(view.context).showSoftInput(view, flags)
}

fun getInputManager(context: Context): InputMethodManager {
  return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}
