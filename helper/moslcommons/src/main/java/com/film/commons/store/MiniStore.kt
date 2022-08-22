package com.film.commons.store

import com.film.commons.cache.CacheOptions
import com.film.commons.cache.CacheOptions.Companion.NoCache
import com.film.commons.cache.InMemoryCache
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.ConcurrentHashMap

interface MiniStore<in K : Any, V> {
  /**
   * Returns a Single of V for requested key
   * Data will be returned from Memory Cache, Inflight, Network Response
   */
  fun get(key: K): Single<V>

  /**
   * Returns a Single of V for requested key skipping Memory Cache
   */
  fun fetch(key: K): Single<V>
}

class RealMiniStore<in Key : Any, Value : Any>(
    protected val fetcher: (Key) -> Single<Value>,
    protected val cacheOptions: CacheOptions,
    protected val cache: InMemoryCache
) : MiniStore<Key, Value> {
  private val inFlightRequests: ConcurrentHashMap<Key, Single<Value>> = ConcurrentHashMap()

  override fun get(key: Key): Single<Value> {
    return cache.get<Value>(key = key)
        .switchIfEmpty(fetch(key))
  }

  override fun fetch(key: Key): Single<Value> {
    return inFlightRequests[key]
        ?: fetcher.invoke(key)
            .doOnSuccess {
              if (cacheOptions != NoCache) {
                cache.set(key = key, value = it, options = cacheOptions)
              }
            }
            .doAfterTerminate { inFlightRequests.remove(key) }
            .cache().also {
              inFlightRequests[key] = it
            }
  }

}