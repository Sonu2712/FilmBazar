package com.film.app.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import io.reactivex.rxjava3.core.Observable

interface NetworkListener {
  fun register()
  fun unRegister()
  fun onNetworkChanged(): Observable<NetworkChangeEvent>

  companion object {
    fun createNetworkListener(context: Context): NetworkListener {
      val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        NetworkInterceptor(cm)
      } else {
        NetworkReceiver(context, cm)
      }
    }
  }
}