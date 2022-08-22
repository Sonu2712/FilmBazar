package com.film.commons.cache

import com.film.commons.cache.CacheOptions.Companion.UserCacheForHour
import com.film.commons.utils.Clock
import io.reactivex.rxjava3.core.Maybe
import java.util.concurrent.ConcurrentHashMap

class InMemoryCache(protected val clock: Clock) {
  private val memoryCache: ConcurrentHashMap<Any, CacheEntry> = ConcurrentHashMap()

  fun clearUserEntries() {
    val usersEntries = memoryCache.filter { it.value.forUser() }
        .keys
    usersEntries.forEach(this::evict)
  }

  fun clearExpired() {
    val expiredKeys = memoryCache.filter { it.value.isExpired() }
        .keys
    expiredKeys.forEach(this::evict)
  }

  fun evict(key: Any) {
    memoryCache.remove(key)
  }

  fun evictAll() {
    memoryCache.clear()
  }

  @JvmOverloads
  fun set(
      key: Any,
      value: Any,
      options: CacheOptions = UserCacheForHour
  ) {
    memoryCache[key] = CacheEntry(clock.getTimestamp(), value, options)
  }

  @Suppress("UNCHECKED_CAST")
  fun <V : Any> getValue(key: Any): V? {
    removeIfExpired(key)
    return memoryCache[key]?.let {
      it.data as V
    }
  }

  fun contains(key: Any): Boolean {
    removeIfExpired(key)
    return memoryCache.contains(key)
  }

  @Suppress("UNCHECKED_CAST")
  fun <V : Any> get(key: Any): Maybe<V> {
    removeIfExpired(key)
    return memoryCache[key]?.let {
      Maybe.just(it.data as V)
    } ?: Maybe.empty<V>()
  }

  private fun removeIfExpired(key: Any) {
    val entry = memoryCache[key] ?: return
    if (entry.isExpired()) {
      evict(key)
    }
  }

  private fun CacheEntry.isExpired(): Boolean {
    val currentTime = clock.getTimestamp()
    val lastUpdateTime = entryTime
    return lastUpdateTime == 0L || currentTime - lastUpdateTime > options.validity
  }
}
