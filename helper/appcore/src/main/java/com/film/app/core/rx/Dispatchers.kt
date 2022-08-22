@file:JvmName("RxDispatchers")
package com.film.app.core.rx

import com.film.commons.data.UiModel
import com.film.commons.data.applyRvUiModel
import com.film.commons.data.applyUiModel
import com.film.commons.rx.Dispatchers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

@JvmField
val DefaultDispatchers = Dispatchers(
    io = Schedulers.io(),
    computation = Schedulers.computation(),
    main = AndroidSchedulers.mainThread(),
    db = Schedulers.from(Executors.newSingleThreadExecutor {
      Thread(it, "db").apply { isDaemon = true }
    })
)

fun <T> applyUiModel(): ObservableTransformer<T, UiModel<T>> {
  return DefaultDispatchers.applyUiModel()
}

fun <T> applyRvUiModel(): ObservableTransformer<T, UiModel<T>> {
  return DefaultDispatchers.applyRvUiModel()
}