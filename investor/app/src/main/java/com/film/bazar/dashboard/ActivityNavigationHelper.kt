package com.film.bazar.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.film.annotations.ActivityScoped
import com.film.app.core.prefs.StringPreference
import com.film.annotations.AppShortCutId
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.core.MOSLCommonActivity
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.navigator.UrlNavigator
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.helper.CustomNavigatorImpl
import com.film.bazar.coreui.navigatorlib.NavigationHelper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class ActivityNavigationHelper @Inject constructor(
    internal val screenNavigator: ScreenNavigator,
    customNavigator: CustomNavigatorImpl,
    private val navigationHelper: NavigationHelper,
    internal val urlNavigator: UrlNavigator,
    val context: Context,
    @AppShortCutId val appShortCutId: StringPreference
) : Disposable {
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var pendingMenuAction: AppMenu? = null

    init {
        disposable.add(customNavigator)
    }

    fun loadIntent(intent: Intent?) {
        intent ?: return
        Timber.i("Intent Received %s ", intent.toString())
        when {
            intent.data != null -> {
                loadUri(intent.data)
            }
            intent.extras.getPageId() == "none" -> screenNavigator.openHome()
            else -> intent.extras?.setupFragment()
        }
    }

    private fun openHomePageIfNecessary() {
        if (navigationHelper.currentFragment == null) {
            Timber.i("Opening home")
            screenNavigator.openHome()
        }
    }

    fun loadUri(uri: Uri?) {
        uri ?: return
        urlNavigator.openUri(uri)
    }

    fun onMenuClicked(appMenu: AppMenu) {
        if (appMenu.enabled) {
            pendingMenuAction = appMenu
        } else {
            screenNavigator.pageNotAccessible()
        }
    }

    fun handlePendingMenuAction() {
        pendingMenuAction?.let {
            loadFragment(it.pageId)
        }
        pendingMenuAction = null
    }

    fun loadFragment(@NavigationConstants pageId: String) {
        urlNavigator.openUrl(pageId, false)
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }

    override fun dispose() {
        disposable.dispose()
    }

    private fun Bundle.setupFragment(addToStack: Boolean = false) {
        appShortCutId.set(getPageId())
        screenNavigator.openPage(getPageId(), this, addToStack)
    }

    companion object {
        internal fun Bundle?.getPageId(): String {
            return this?.getString(
                MOSLCommonActivity.ARG_PAGE_ID,
                NavigationConstants.NAVIGATE_TO_NONE
            )
                ?: NavigationConstants.NAVIGATE_TO_NONE
        }
    }
}