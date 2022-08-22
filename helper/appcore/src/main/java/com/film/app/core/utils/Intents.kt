@file:JvmName("Intents")

package com.film.app.core.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.film.app.core.R
import timber.log.Timber

/**
 * Attempt to launch the supplied [Intent]. Queries on-device packages before launching
 * and
 * will display a simple message if none are available to handle it.
 */
fun Context.maybeStartActivity(intent: Intent): Boolean {
  return this.maybeStartActivity(intent, false)
}

/**
 * Attempt to launch Android's chooser for the supplied [Intent]. Queries on-device
 * packages before launching and will display a simple message if none are available to handle
 * it.
 */
fun Context.maybeStartChooser(intent: Intent): Boolean {
  return this.maybeStartActivity(intent, true)
}

private fun Context.maybeStartActivity(intent: Intent, chooser: Boolean): Boolean {
  var newIntent = intent
  return if (this.hasHandler(intent)) {
    if (chooser) {
      newIntent = Intent.createChooser(intent, null)
    }
    startActivity(newIntent)
    true
  } else {
    Toast.makeText(this, R.string.no_intent_handler, LENGTH_LONG).show()
    false
  }
}

/**
 * it check if the bundle is null or empty.
 * it allows to skip presence of any custom keys (for other purposes)
 * and still return empty if only custom keys are present.
 */
fun Bundle?.isEmpty(): Boolean {
  return this == null || this == Bundle.EMPTY || isEmpty
}

fun Bundle?.dump() {
  if (this != null) {
    val builder = StringBuilder()
    val keys = keySet()
    builder.append("[")
    for (key in keys) {
      builder.append(key).append(" = ").append(get(key)).append(", ")
    }
    builder.append("]")
    Timber.d("Arguments Passed %s", builder.toString())
  }
}

/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
private fun Context.hasHandler(intent: Intent): Boolean {
  val handlers = packageManager.queryIntentActivities(intent, 0)
  return !handlers.isEmpty()
}