package com.film.commons.cache

import java.util.concurrent.TimeUnit.HOURS

data class CacheOptions @JvmOverloads constructor(
    @JvmField val validity: Long,
    @JvmField val forUser: Boolean = false
) {
  companion object {
    @JvmField
    val CacheForHour = CacheOptions(HOURS.toMillis(1))

    @JvmField
    val UserCacheForHour: CacheOptions = CacheForHour.copy(forUser = true)

    @JvmField
    val NoCache = CacheOptions(0)
  }
}

data class CacheEntry(
    @JvmField val entryTime: Long,
    @JvmField val data: Any,
    @JvmField val options: CacheOptions
) {
  fun forUser() = options.forUser
}