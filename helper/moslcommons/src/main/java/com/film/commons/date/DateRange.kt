package com.film.commons.date

import java.util.Date

data class DateRange(
    @JvmField val start: Date,
    @JvmField val end: Date
) {
  constructor(datePair: Pair<Date, Date>) : this(datePair.first, datePair.second)

  fun getPair(): Pair<Date, Date> {
    return Pair(start, end)
  }

  fun contains(date: Date): Boolean {
    return date.after(start) && date.before(end)
  }

  fun allInclusive(): DateRange {
    return DateRange(
        start = start.startOfDay(),
        end = end.endOfDay()
    )
  }
}