package com.film.commons.cache

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

class ReactiveCache<T> {
  private var data: T? = null

  fun clear() {
    this.data = null
  }

  fun setData(data: T) {
    this.data = data
  }

  fun get(): T? {
    return data
  }

  fun getData(): Maybe<T> {
    return data?.let {
      Maybe.just(it)
    } ?: Maybe.empty()
  }
}

fun <T> ReactiveCache<T>.getOrCreate(
    networkObs: Observable<T>,
    skipCache: Boolean = false
): Observable<T> {
  return if (skipCache) {
    networkObs.doOnNext { setData(it) }
  } else {
    getData()
        .switchIfEmpty(networkObs.doOnNext { setData(it) }.singleElement())
        .toObservable()
  }
}