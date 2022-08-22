package com.film.login_ui.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.film.login.data.LoginSuccess
import com.film.login.data.MarketsEquityRequested
import com.film.login.data.PasswordChangeNeeded
import com.film.login_ui.nav.LoginConstants
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.coreui.navigatorlib.*
import timber.log.Timber
import javax.inject.Inject

class LoginNavigatorImpl @Inject constructor(
    val activity: AppCompatActivity,
    val navigator: Navigator
) : LoginNavigator {

    override fun openHome() {
        activity.setResult(LoginSuccess)
        activity.finish()
    }

    override fun forceChangePassword(targetPageId: String, bundle: Bundle) {
        Timber.i("Customer Force Change Password Target page Id $targetPageId and data $bundle")
        val intent = if (targetPageId != "") Intent().apply {
            putExtra(LoginActivity.ARG_LOGIN_TARGET_PAGE_ID, targetPageId)
            putExtra(LoginActivity.ARG_LOGIN_TARGET_PAGE_DATA, bundle)
        } else null

        Timber.i("Force Changed Password : $intent")
        activity.setResult(PasswordChangeNeeded, intent)
        activity.finish()
    }

    override fun login(userType: UserType) {
        if (userType.isGuest) {
            openPage(LoginConstants.NAVIGATE_TO_GUEST_LOGIN, false)
        } else {
            openPage(LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN, false)
        }
    }

    override fun goBack() {
        openPage(FragmentNavigationEntry.goBack())
    }

    override fun goBack(result: Bundle) {
        openPage(FragmentNavigationEntry.goBackWithResult(result))
    }

    override fun finish() {
        activity.setResult(Activity.RESULT_CANCELED)
        activity.finish()
    }

    override fun openPage(
        pageId: String,
        addToStack: Boolean
    ) {
        openPage(pageId, Bundle.EMPTY, addToStack)
    }

    override fun openPage(
        pageId: String,
        args: Bundle,
        addToStack: Boolean,
        forceReload: Boolean
    ) {
        openPage(NavigationRequest(pageId, args, addToStack, forceReload))
    }

    override fun openPage(request: NavigationRequest) {
        handleNavigationRequest(request)
    }

    override fun openPage(entry: NavigationEntry) {
        processNavigation(entry)
    }

    protected fun handleNavigationRequest(request: NavigationRequest) {
        processNavigation(request.asFragmentEntry())
    }

    protected fun processNavigation(entry: NavigationEntry?) {
        entry ?: return
        when (entry) {
            is FragmentNavigationEntry -> {
                navigator.gotoPage(entry)
            }
            else -> {
                Timber.e("IllegalState : LoginNavigator only supports fragment Navigation")
            }
        }
    }

    override fun pageNotAccessible() {}

    override fun openUrl(context: Context, url: String) {
        val uri = Uri.parse(url)
        Timber.i("Open Uri : %s", uri)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun navigateToMarkets() {
        Timber.i("View Markets Clicked from Login Screen")
        activity.setResult(MarketsEquityRequested)
        activity.finish()
    }
}