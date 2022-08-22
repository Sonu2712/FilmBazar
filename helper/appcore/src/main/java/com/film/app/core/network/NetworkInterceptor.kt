package com.film.app.core.network

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable

@RequiresApi(VERSION_CODES.N)
class NetworkInterceptor(
    val cm: ConnectivityManager
) : NetworkCallback(), NetworkListener {
  private val networkStateSubject: BehaviorRelay<NetworkChangeEvent> = BehaviorRelay.create()

  override fun onAvailable(network: Network) {
    networkStateSubject.accept(NetworkChangeEvent.Connected)
  }

  override fun onLost(network: Network) {
    networkStateSubject.accept(NetworkChangeEvent.Disconnected)
  }

  override fun onNetworkChanged(): Observable<NetworkChangeEvent> {
    return networkStateSubject
  }

  override fun register() {
    cm.registerDefaultNetworkCallback(this)
  }

  override fun unRegister() {
    cm.unregisterNetworkCallback(this)
  }
}