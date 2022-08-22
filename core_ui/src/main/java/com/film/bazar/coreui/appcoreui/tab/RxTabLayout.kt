package com.film.bazar.coreui.appcoreui.tab

import android.os.Looper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import io.reactivex.rxjava3.android.MainThreadDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun TabLayout.selections(): Observable<Tab> {
    return TabLayoutSelectionsObservable(this)
}

class TabLayoutSelectionsObservable(
    protected val view: TabLayout
) : Observable<Tab>() {
    override fun subscribeActual(observer: Observer<in Tab>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.addOnTabSelectedListener(listener)
        val index = view.selectedTabPosition
        if (index != -1) {
            observer.onNext(view.getTabAt(index)!!)
        }
    }

    internal class Listener(
        private val tabLayout: TabLayout,
        private val observer: Observer<in Tab>
    ) : MainThreadDisposable(), OnTabSelectedListener {

        override fun onDispose() {
            tabLayout.removeOnTabSelectedListener(this)
        }

        override fun onTabSelected(tab: Tab) {
            if (!isDisposed) {
                observer.onNext(tab)
            }
        }

        override fun onTabUnselected(tab: Tab) {}

        override fun onTabReselected(tab: Tab) {}
    }

}

fun checkMainThread(observer: Observer<*>): Boolean {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        observer.onSubscribe(Disposable.empty())
        observer.onError(
            IllegalStateException(
                "Expected to be called on the main thread but was " + Thread.currentThread().name
            )
        )
        return false
    }
    return true
}