package com.film.commons.cache

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.ConcurrentHashMap

class ReactiveMultiCache {
  private val memoryCache: ConcurrentHashMap<Any, Any> = ConcurrentHashMap()

  fun evict(key: Any) {
    memoryCache.remove(key)
  }

  fun evictAll() {
    memoryCache.clear()
  }

  fun set(
      key: Any,
      value: Any
  ) {
    memoryCache[key] = value
  }

  fun contains(key: Any): Boolean {
    return memoryCache.contains(key)
  }

  @Suppress("UNCHECKED_CAST")
  fun <V : Any> getValue(key: Any): V? {
    return memoryCache[key]?.let {
      it as V
    }
  }

  @Suppress("UNCHECKED_CAST")
  fun <V : Any> get(key: Any): Maybe<V> {
    return memoryCache[key]?.let {
      Maybe.just(it as V)
    } ?: Maybe.empty<V>()
  }
}

@JvmOverloads
fun <K : Any, V : Any> ReactiveMultiCache.getOrCreate(
    key: K,
    networkObs: Observable<V>,
    skipCache: Boolean = false
): Observable<V> {
  return if (skipCache) {
    networkObs.doOnNext { set(key = key, value = it) }
  } else
    get<V>(key = key)
        .switchIfEmpty(
            networkObs.doOnNext { set(key = key, value = it) }.singleElement()
        )
        .toObservable()
}

fun <K : Any, V : Any> ReactiveMultiCache.getOrCreate(
    key: K,
    networkObs: Maybe<V>
): Observable<V> {
  return get<V>(key = key)
      .switchIfEmpty(
          networkObs.doOnSuccess { set(key = key, value = it) })
      .toObservable()
}