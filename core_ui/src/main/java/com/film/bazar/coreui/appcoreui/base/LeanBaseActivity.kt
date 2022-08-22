package com.film.bazar.coreui.appcoreui.base

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.film.app.core.base.ViewContainer
import com.film.app.core.rx.DefaultDispatchers
import com.film.app.core.utils.actionableAlert
import com.film.app.core.utils.alert
import com.film.app.core.utils.hideKeyBoard
import com.film.bazar.coreui.R
import com.film.bazar.coreui.appcoreui.dialog.showToastForWarning
import com.film.commons.rx.Dispatchers
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

abstract class LeanBaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewContainer: ViewContainer

    @JvmField
    protected val disposable = CompositeDisposable()

    @JvmField
    protected val dispatchers: Dispatchers = DefaultDispatchers

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentFocus = currentFocus
        if (currentFocus != null) {
            hideKeyBoard(currentFocus, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        return super.dispatchTouchEvent(ev)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun removeStatusBarColor(window: Window) {
        window.statusBarColor = Color.TRANSPARENT
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = viewContainer.forActivity(this)
        layoutInflater.inflate(getLayout(), container)
        if (clearWindowBackground()) {
            window.setBackgroundDrawable(null)
        }
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    protected open fun clearWindowBackground(): Boolean {
        return true
    }

    @JvmOverloads
    fun alert(
        message: String,
        @StringRes title: Int = R.string.alert_dialog_title
    ): AlertDialog {
        return alert(message = message, title = getString(title))
    }

    @JvmOverloads
    fun actionableAlert(
        message: String,
        cancelable: Boolean = false,
        @StringRes buttonText: Int = R.string.button_label_ok,
        callback: () -> Unit
    ): AlertDialog {
        return actionableAlert(
            message = message,
            cancelable = cancelable,
            positiveButton = buttonText,
            callback = callback
        )
    }

    fun toast(message: String) {
        this.showToastForWarning(message)
    }
}