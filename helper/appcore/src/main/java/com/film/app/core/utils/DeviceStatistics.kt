package com.film.app.core.utils

import android.os.Build
import java.util.TimeZone

/**
 * @author Sasikumar
 */
object DeviceStatistics {

  val timeZoneID: String
    get() {
      val tz = TimeZone.getDefault()
      return tz.id
    }

  val deviceModel: String
    get() = Build.MODEL

  val deviceVendor: String
    get() = Build.MANUFACTURER

  /**
   * @return <br></br>
   * Ex:- For 2.2 - 8, 4.0 - 14
   */
  val osVersionCode: Int
    get() = Build.VERSION.SDK_INT

  /**
   * @return <br></br>
   * Ex:- 2.2.1, 2.3,...
   */
  val osVersionNumber: String
    get() = Build.VERSION.RELEASE
}
