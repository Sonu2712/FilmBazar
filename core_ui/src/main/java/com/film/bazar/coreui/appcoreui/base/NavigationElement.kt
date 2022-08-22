package com.film.bazar.coreui.appcoreui.base

import com.film.app.core.network.NetworkChangeEvent
import com.film.bazar.coreui.navigatorlib.NavigationElement


interface NavigationElementCore : com.film.bazar.coreui.navigatorlib.NavigationElement {
  fun networkChanged(info: NetworkChangeEvent)
}
