package com.film.commons.store

import com.film.commons.cache.CacheOptions
import com.film.commons.cache.CacheOptions.Companion.NoCache
import com.film.commons.cache.CacheOptions.Companion.UserCacheForHour
import com.film.commons.cache.InMemoryCache
import io.reactivex.rxjava3.core.Single

class StoreFactory(
    private val cache: InMemoryCache
) {
  fun <Key : Any, Value : Any> create(
      fetcher: (Key) -> Single<Value>
  ): MiniStore<Key, Value> {
    return createCached(NoCache, fetcher)
  }

  fun <Key : Any, Value : Any> createCached(
      cacheOptions: CacheOptions = UserCacheForHour,
      fetcher: (Key) -> Single<Value>
  ): MiniStore<Key, Value> {
    return RealMiniStore(fetcher, cacheOptions, cache)
  }
}