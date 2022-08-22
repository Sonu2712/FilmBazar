package com.film.commons.cache

import com.film.commons.cache.CacheOptions.Companion.UserCacheForHour
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

@JvmOverloads
fun <K : Any, V : Any> InMemoryCache.getOrCreate(
    key: K,
    networkObs: Observable<V>,
    options: CacheOptions = UserCacheForHour,
    skipCache: Boolean = false
): Observable<V> {
  return if (skipCache) {
    networkObs.doOnNext { set(key = key, value = it, options = options) }
  } else
    get<V>(key = key)
        .switchIfEmpty(
            networkObs.doOnNext { set(key = key, value = it, options = options) }.singleElement()
        )
        .toObservable()
}

fun <K : Any, V : Any> InMemoryCache.getOrCreate(
    key: K,
    networkObs: Maybe<V>,
    options: CacheOptions = UserCacheForHour
): Observable<V> {
  return get<V>(key = key)
      .switchIfEmpty(
          networkObs.doOnSuccess { set(key = key, value = it, options = options) })
      .toObservable()
}
