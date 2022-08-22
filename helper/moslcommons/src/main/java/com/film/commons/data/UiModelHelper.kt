@file:JvmName("UiModelHelper")

package com.film.commons.data

import com.film.commons.data.UiModel.Fail
import com.film.commons.data.UiModel.Success
import com.film.commons.rx.Dispatchers
import com.film.commons.rx.ofType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer

fun <T> Dispatchers.applyUiModel(): ObservableTransformer<T, UiModel<T>> {
    return ObservableTransformer { observable ->
        observable.map { UiModel.success(it) }
            .onErrorReturn { UiModel.error(it) }
            .observeOn(main)
            .startWithItem(UiModel.InProgress)
    }
}

fun <T> Dispatchers.applyRvUiModel(): ObservableTransformer<T, UiModel<T>> {
    return ObservableTransformer { observable ->
        observable.map { UiModel.success(it) }
            .onErrorReturn { UiModel.error(it) }
            .startWithItem(UiModel.InProgress)
            .observeOn(main)
    }
}

inline fun <T> UiModel<T>.onSuccess(action: (data: T) -> Unit) {
    if (this is Success) {
        action(this.value)
    }
}

inline fun UiModel<*>.onFailure(action: (error: Throwable) -> Unit) {
    if (this is Fail) {
        action(this.throwable)
    }
}

/**
 * Filter all type of Error generated UpStream
 */
fun <T> Observable<T>.skipErrors(): Observable<T> {
    return map { UiModel.success(it) }
        .onErrorReturn { UiModel.error(it) }
        .filterSuccess()
}

/**
 * Filter only succeeded UiModel events unwrapped to T type.
 */
fun <T> Observable<UiModel<T>>.filterSuccess(): Observable<T> {
    return ofType<UiModel.Success<T>>()
        .map { it.value }
}

/**
 * Filter only failed UiModel events unwrapped to Throwable type
 */
fun <T> Observable<UiModel<T>>.filterFailure(): Observable<Throwable> {
    return ofType<UiModel.Fail<T>>()
        .map { it.throwable }
}