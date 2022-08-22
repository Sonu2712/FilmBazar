package com.film.app.core.base

import androidx.annotation.CallSuper
import com.film.app.core.events.DataAction
import com.film.app.core.rx.DefaultDispatchers
import com.film.commons.data.applyRvUiModel
import com.film.commons.data.applyUiModel
import com.film.commons.rx.Dispatchers
import com.film.commons.rx.ofType
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit.MILLISECONDS

abstract class BasePresenter<out T : BaseView> @JvmOverloads constructor(
    @JvmField protected val view: T,
    @JvmField protected val dispatchers: Dispatchers = DefaultDispatchers
) {
  @JvmField
  protected val disposable: CompositeDisposable = CompositeDisposable()

  fun onFetchCalled(): Observable<out DataAction.Fetch> {
    return view.onDataAction().ofType()
  }

  fun onRetryCalled(): Observable<out DataAction.Retry> {
    return view.onDataAction().ofType<DataAction.Retry>()
        .filter { view.isDataEmpty() }
  }

  fun onRefreshCalled(): Observable<out DataAction.Refresh> {
    return view.onDataAction().ofType()
  }

  fun onFetchOrRetry(timeout: Long): Observable<DataAction> {
    return Observable.merge(onFetchCalled(), onRetryCalled())
        .debounce(timeout, MILLISECONDS, dispatchers.main)
  }

  fun onRetrySection(sectionId: Int): Observable<out DataAction.RetrySection> {
    return view.onDataAction().ofType<DataAction.RetrySection>()
        .filter { (id) -> id == sectionId }
  }

  fun <T> applyUiModel() = dispatchers.applyUiModel<T>()

  fun <T> applyRvUiModel() = dispatchers.applyRvUiModel<T>()

  abstract fun start()

  @CallSuper
  open fun stop() {
    disposable.clear()
  }
}