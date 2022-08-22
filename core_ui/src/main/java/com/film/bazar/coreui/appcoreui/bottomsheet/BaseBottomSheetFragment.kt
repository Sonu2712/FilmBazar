package com.film.bazar.coreui.appcoreui.bottomsheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.film.app.core.events.DataAction
import com.film.app.core.network.isNetworkConnected
import com.film.app.core.rx.DefaultDispatchers
import com.film.bazar.coreui.R
import com.film.bazar.coreui.appcoreui.dialog.showToastForError
import com.film.bazar.coreui.appcoreui.dialog.showToastForSuccess
import com.film.bazar.coreui.appcoreui.dialog.showToastForWarning
import com.film.commons.rx.Dispatchers
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {
    @JvmField
    protected val dataActionSubject: PublishSubject<DataAction> =
        PublishSubject.create<DataAction>()

    @JvmField
    protected val onRetryClick = View.OnClickListener {
        if (isConnected()) {
            sendServiceRequest()
        } else {
            requireActivity().showToastForWarning(getString(R.string.check_connection))
        }
    }

    @JvmField
    protected val disposable: CompositeDisposable = CompositeDisposable()

    @JvmField
    protected val dispatchers: Dispatchers = DefaultDispatchers

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val contentView = View.inflate(context, getLayout(), null)
        dialog.setContentView(contentView)

        setupView(contentView)

        val behavior =
            ((contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        return dialog
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun setupView(view: View)

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

    fun showErrorToast(message: String) {
        requireActivity().showToastForError(message)
    }

    fun showSuccessToast(message: String) {
        requireActivity().showToastForSuccess(message)
    }

    @CallSuper
    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }
}

typealias DaggerBaseBottomSheetFragment = BaseBottomSheetFragment