@file:JvmName("DateFormatUtils")

package com.film.commons.date

import java.text.DateFormat
import java.util.Date

fun DateFormat.formatOrNull(date: Date?): String? {
  if (date == null) return null
  return try {
    format(date)
  } catch (e: Exception) {
    null
  }
}

@JvmOverloads
fun DateFormat.formatOrDefault(date: Date?, default: String = ""): String {
  if (date == null) return default
  return try {
    format(date)
  } catch (e: Exception) {
    default
  }
}

fun DateFormat.parseOrNull(input: String?): Date? {
  if (input.isNullOrBlank()) return null
  return try {
    parse(input)
  } catch (e: Exception) {
    null
  }
}

@JvmOverloads
fun DateFormat.parseOrDefault(input: String?, defaultDate: Date = Date()): Date {
  if (input.isNullOrBlank()) return defaultDate
  return try {
    parse(input)
  } catch (e: Exception) {
    defaultDate
  }
}