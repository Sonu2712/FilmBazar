package com.film.commons.date

import java.util.Calendar
import java.util.Date

object CalendarUtils {
  @JvmStatic
  val previousYear: Date
    get() {
      return Calendar.getInstance().apply {
        add(Calendar.YEAR, -1)
      }.time
    }

  @JvmStatic
  val yesterday: Date
    get() {
      return Calendar.getInstance().apply {
        add(Calendar.DATE, -1)
      }.time
    }

  @JvmStatic
  val today: Date
    get() {
      return Calendar.getInstance().time
    }

  @JvmStatic
  val tomorrow: Date
    get() {
      return Calendar.getInstance().apply {
        add(Calendar.DATE, 1)
      }.time
    }

  @JvmStatic
  val epochDate: Date
    get() {
      return Calendar.getInstance().apply {
        set(1970, 0, 1)
      }.time
    }
}