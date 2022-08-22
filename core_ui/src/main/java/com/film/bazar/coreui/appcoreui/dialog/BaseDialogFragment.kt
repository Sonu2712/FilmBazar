package com.film.bazar.coreui.appcoreui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import com.film.app.core.events.DataAction
import com.film.app.core.network.isNetworkConnected
import com.film.app.core.rx.DefaultDispatchers
import com.film.app.core.utils.toast
import com.film.bazar.coreui.R
import com.film.commons.rx.Dispatchers
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseDialogFragment : AppCompatDialogFragment() {
  @JvmField
  protected val dataActionSubject: PublishSubject<DataAction> =
      PublishSubject.create<DataAction>()
  @JvmField
  protected val onRetryClick = View.OnClickListener {
    if (isConnected()) {
      sendServiceRequest()
    } else {
      requireContext().toast(getString(R.string.check_connection))
    }
  }
  @JvmField
  protected val disposable: CompositeDisposable = CompositeDisposable()
  @JvmField
  protected val dispatchers: Dispatchers = DefaultDispatchers

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  @CallSuper
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(getLayout(), container, false)
  }

  @LayoutRes
  protected abstract fun getLayout(): Int

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    extractArguments(arguments ?: Bundle.EMPTY)
  }

  open fun extractArguments(bundle: Bundle) {}

  open fun sendServiceRequest() {
    dataActionSubject.onNext(DataAction.Retry)
  }

  fun onDataAction(): Observable<DataAction> {
    return dataActionSubject
  }

  open fun isDataEmpty(): Boolean {
    return false
  }

  fun isConnected(): Boolean {
    return requireContext().isNetworkConnected()
  }

  @CallSuper
  override fun onDestroyView() {
    disposable.clear()
    super.onDestroyView()
  }
}

typealias DaggerBaseDialogFragment = BaseDialogFragment