@file:JvmName("RxUtils")

package com.film.commons.rx

import com.film.commons.optional.Optional
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.annotations.SchedulerSupport
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Predicate

@JvmField
val StringIsEmpty: Predicate<CharSequence> = Predicate { it.isEmpty() }

@JvmField
val StringIsNotEmpty: Predicate<CharSequence> = Predicate { it.isNotEmpty() }

@JvmField
val StringIsBlank: Predicate<CharSequence> = Predicate { it.isBlank() }

@JvmField
val StringIsNotBlank: Predicate<CharSequence> = Predicate { it.isNotBlank() }

@JvmField
val PanNumberPredicate: Predicate<CharSequence> = Predicate { it.length == 10 }

@JvmField
val IfscCodePredicate: Predicate<CharSequence> = Predicate { it.length == 10 }

@JvmField
val InvalidSelectionPredicate: Predicate<Int> = Predicate { it != -1 }

@JvmField
val CharSequenceToString: Function<CharSequence, String> = Function {
  it.toString().trim()
}

@JvmField
val MapStringIsBlank: Function<CharSequence, Boolean> = Function { it.isBlank() }

@JvmField
val MapStringIsNotBlank: Function<CharSequence, Boolean> = Function { it.isNotBlank() }

/**
 * Use this instead of map where mapper may return potentially nullable value.
 */
fun <T, R> Observable<T>.safeMap(mapper: (T) -> R?): Observable<R> {
  return map { Optional.ofNullable(mapper(it)) }
      .filter(Optional<R>::isPresent)
      .map(Optional<R>::get)
}

/**
 * Add the disposable to a CompositeDisposable.
 * @param compositeDisposable CompositeDisposable to add this disposable to
 * @return this instance
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable = apply { compositeDisposable.add(this) }

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
inline fun <reified R : Any> Observable<*>.ofType(): Observable<R> = ofType(R::class.java)

fun <T, R> Observable<T>.distinctKey(mapper: (T) -> R): Observable<R> {
  return map(mapper)
      .distinctUntilChanged()
}