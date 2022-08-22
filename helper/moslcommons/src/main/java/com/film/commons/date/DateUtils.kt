@file:JvmName("DateUtils")

package com.film.commons.date

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.startOfDay(): Date {
  return Calendar.getInstance().apply {
    time = this@startOfDay
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
  }.time
}

fun Date.endOfDay(): Date {
  return Calendar.getInstance().apply {
    time = this@endOfDay
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    add(Calendar.DATE, 1)
    add(Calendar.SECOND, -1)
  }.time
}

val Year: DateFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)

fun Date.isValidPastDate(): Boolean {
  val year = Year.format(this).toInt()
  return if (this.after(CalendarUtils.today)) {
    false
  } else year in 1970..2100
}

fun Date.isValidFutureDate(): Boolean {
  val year = Year.format(this).toInt()
  return if (this.before(CalendarUtils.today)) {
    false
  } else year <= 2100
}