@file:JvmName("Utils")

package com.film.app.core.utils

import android.os.SystemClock
import android.text.format.Formatter
import android.util.Base64
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.NetworkInterface
import java.net.SocketException
import java.text.NumberFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

fun isNumeric(str: String?): Boolean {
  if (str.isNullOrBlank()) return false
  val formatter = NumberFormat.getInstance()
  val pos = ParsePosition(0)
  formatter.parse(str, pos)
  return str.length == pos.index
}

fun wasDeviceRebooted(savedTime: Long): Boolean {
  val realTime = SystemClock.elapsedRealtime()
  return savedTime >= realTime
}

fun isEmailValid(email: String): Boolean {
  val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
      + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
      + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
      + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
      + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
      + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
  val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
  val matcher = pattern.matcher(email)
  return matcher.matches()
}

fun getLocalIpAddress(): String {
  try {
    val en = NetworkInterface.getNetworkInterfaces()
    while (en.hasMoreElements()) {
      val intf = en.nextElement()
      val enumIpAddr = intf.inetAddresses
      while (enumIpAddr.hasMoreElements()) {
        val inetAddress = enumIpAddr.nextElement()
        if (!inetAddress.isLoopbackAddress) {
          return Formatter.formatIpAddress(inetAddress.hashCode())
        }
      }
    }
  } catch (ex: SocketException) {
    Timber.e(ex.toString())
  }
  return "0.0.0.0"
}

fun getAuthToken(userCode: String): String {
  var authToken: String
  val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH)
  val currentDateTime = sdf.format(Date())
  authToken = userCode + "_" + currentDateTime
  try {
    val encode1 = Base64.encodeToString(authToken.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    authToken = Base64.encodeToString(encode1.toByteArray(charset("UTF-8")), Base64.DEFAULT)
  } catch (e: UnsupportedEncodingException) {
    e.printStackTrace()
  }

  return authToken
}