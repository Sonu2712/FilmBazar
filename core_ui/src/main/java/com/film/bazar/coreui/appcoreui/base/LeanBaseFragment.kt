package com.film.bazar.coreui.appcoreui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.film.app.core.utils.hideKeyBoard
import com.film.app.core.utils.showKeyBoard
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

abstract class LeanBaseFragment : DaggerFragment() {
  @JvmField
  val disposable: CompositeDisposable = CompositeDisposable()

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(getLayout(), container, false)
  }

  @LayoutRes
  protected abstract fun getLayout(): Int

  @CallSuper
  override fun onDestroyView() {
    disposable.clear()
    super.onDestroyView()
  }

  protected fun showKeyboard(view: View?) {
    if (view == null) return
    showKeyBoard(view)
  }

  protected fun hideKeyboard(view: View?) {
    if (view == null) return
    hideKeyBoard(view)
  }

  open fun onFragmentResult(data: Bundle) {
    Timber.d("Result : %s on Page : %s", data, this)
  }
}