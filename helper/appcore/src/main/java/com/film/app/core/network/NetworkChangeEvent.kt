package com.film.app.core.network

sealed class NetworkChangeEvent {
  object Connected : NetworkChangeEvent() {
    override fun toString(): String {
      return "CONNECTED"
    }
  }

  object Disconnected : NetworkChangeEvent() {
    override fun toString(): String {
      return "DISCONNECTED"
    }
  }
}
