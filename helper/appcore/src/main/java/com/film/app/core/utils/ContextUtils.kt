@file:JvmName("ContextUtils")

package com.film.app.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import timber.log.Timber

/**
 * Returns the SharedPreferences using the packagename as sharedpreference
 * name
 */
fun Context.getPrefs(): SharedPreferences {
  return getSharedPreferences(packageName, Context.MODE_PRIVATE)
}

/**
 * Get the device's Universally Unique Identifier (UUID).
 */
fun Context.getUuid(): String {
  return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context.getWidthHeight(): String {
  val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val dm = DisplayMetrics()
  wm.defaultDisplay.getMetrics(dm)
  return dm.widthPixels.toString() + " X " + dm.heightPixels
}

@SuppressLint("MissingPermission")
fun Context.playNotificationSound() {
  try {
    val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    RingtoneManager.getRingtone(this, notification)?.play()
    (getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator)?.let {
      if (VERSION.SDK_INT >= VERSION_CODES.O) {
        it.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
      } else {
        it.vibrate(500)
      }
    }
  } catch (e: Exception) {
    Timber.e(e, "playNotificationSound failed")
  }
}

fun Context.toast(message: String) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT)
      .show()
}

fun Context.toast(@StringRes message: Int) {
  Toast.makeText(this, message, Toast.LENGTH_SHORT)
      .show()
}