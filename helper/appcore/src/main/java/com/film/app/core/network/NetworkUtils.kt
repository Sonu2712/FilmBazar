@file:JvmName("NetworkUtils")

package com.film.app.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context.getNetworkInfo(): NetworkInfo? {
  val cm =
      applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  return cm.activeNetworkInfo
}

fun Context.isNetworkConnected(): Boolean {
  return getNetworkInfo()?.isConnected == true
}