package com.film.bazar.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.film.app.core.prefs.StringPreference
import com.film.app.core.utils.alert
import com.film.login.data.LoginRequested
import com.film.login.data.LoginSuccess
import com.film.login.data.PasswordChangeNeeded
import com.film.login_ui.LoginFragmentGetter
import com.film.login_ui.core.LoginActivity
import com.film.login_ui.core.LoginActivity.Companion.ARG_LOGIN_TARGET_PAGE_DATA
import com.film.login_ui.nav.LoginConstants
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_HOME
import com.film.bazar.coredata.CommonBaseUrls
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.helper.CustomNavigationEntry
import com.film.bazar.helper.CustomNavigator
import com.film.bazar.util.openWebUrl
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.coreui.navigatorlib.*
import timber.log.Timber

abstract class ScreenNavigatorInternal(
    protected val activity: AppCompatActivity,
    protected val userManager: UserManager,
    protected val navigationHelper: NavigationHelper,
    protected val clientCode: StringPreference,
    protected val customNavigator: CustomNavigator,
    protected val navigator: Navigator,
    protected val  baseUrls: CommonBaseUrls
) : ScreenNavigator {
    private var pendingNavigationRequest: NavigationRequest? = null

    override fun openPage(request: NavigationRequest) {
        handleNavigationRequest(request)
    }

    override fun reloadHome() {
        if (pendingNavigationRequest == null) {
            val startupScreen = userManager.getUser()?.userConfig?.userSettings?.startupScreen
            val startPageManager = if(startupScreen.isNullOrEmpty())  NAVIGATE_TO_HOME else startupScreen
            openPage(startPageManager)
        }
    }

    private fun handleNavigationRequest(request: NavigationRequest) {
        val userType = userManager.getUserType()
        val pageId = request.pageId

        when {
            pageId == NavigationConstants.NAVIGATE_TO_URL ->
                processNavigation(request.data as UriNavigationEntry)

            MenuGetter.isActivity(pageId) -> processNavigation(request.createActivityNavigationEntry())

            LoginFragmentGetter.isHandled(pageId) -> {
                processNavigation(request.createActivityNavigationEntry())
            }
            else -> {
                if (userType.isAccessible(pageId)) {
                    request.handleNavigationToState()
                } else {
                    if (userType == UserType.OPEN_USER || userType == UserType.GUEST) {
                        loginAndNavigate(request)
                    } else if (userType == UserType.NON_TRADING) {
                        activity.alert(
                            "Being an offline customer you cannot place order through online platform. "
                                + "Kindly get in touch with respective Branch/ Franchisee for trading."
                        )
                    } else {
                        Timber.e(IllegalStateException("Unhandled Navigation Request : $request"))
                    }
                }
            }
        }
    }

    protected fun loginAndNavigate(request: NavigationRequest) {
        pendingNavigationRequest = request
        NavigationRequest(LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN).run {
            processNavigation(this.createActivityNavigationEntry())
        }
    }

    private fun NavigationRequest.handleNavigationToState() {
        val pageId = pageId
        if (userManager.getUserType().isAccessible(pageId)) {
            when {
                MenuGetter.isActivity(pageId) -> {
                    processNavigation(createActivityNavigationEntry())
                }
                MenuGetter.isCustomNavigation(pageId) -> {
                    processNavigation(CustomNavigationEntry(pageId))
                }
                else -> {
                    processNavigation(asFragmentEntry())
                }
            }
        } else {
            Timber.e(IllegalStateException("Not enough rights for page : ${this}"))
        }
    }

    protected fun processNavigation(entry: NavigationEntry?) {
        entry ?: return
        when (entry) {
            is ActivityNavigationEntry -> {
                activity.navigate(entry)
            }
            is FragmentNavigationEntry -> {
                /**
                 * Setting isAddToStack to false if currentFragment is null,
                 * This prevents crash for on first fragment loaded on activity
                 */
                val navEntry = if (navigationHelper.currentFragment == null) {
                    entry.copy(isAddToStack = false)
                } else entry
                navigator.gotoPage(navEntry)
            }

            is CustomNavigationEntry -> {
                customNavigator.handle(entry)
            }

            is UriNavigationEntry -> {
                openHomePageIfNecessary()
                activity.openWebUrl(entry.uri)
            }

            else -> {
                Timber.e("Unhandled Navigation : $entry")
            }
        }
    }

    private fun AppCompatActivity.navigate(entry: ActivityNavigationEntry?) {
        entry ?: return
        val intent = Intent(this, entry.activity).apply {
            putExtras(entry.bundle)
        }
        val requestCode = entry.requestCode
        if (requestCode != null) {
            startActivityForResult(intent, requestCode)
        } else {
            startActivity(intent)
        }
    }

    private fun openHomePageIfNecessary() {
        val pageId =
            if (userManager.getUserType().isCustomer) NavigationConstants.NAVIGATE_TO_HOME else ""
        if (navigationHelper.currentFragment == null ) {
            navigator.gotoPage(
                FragmentNavigationEntry(
                    pageId,
                    Bundle.EMPTY,
                    false
                )
            )
        }
    }

    private fun NavigationRequest.createActivityNavigationEntry(): ActivityNavigationEntry? {
        val pageId = pageId
        return when {
            LoginFragmentGetter.isHandled(pageId) -> {
                val bundle = Bundle(bundle).apply {
                    putString(LoginActivity.ARG_PAGE_ID, pageId)
                }
                bundle.putInt(LoginActivity.ARG_PAGE_MODE, LoginRequested)
                ActivityNavigationEntry(
                    activity = LoginActivity::class.java,
                    bundle = bundle,
                    requestCode = LoginRequested
                )
            }
            else -> null
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == LoginSuccess) {
            pendingNavigationRequest?.handleNavigationToState()
        } else if (resultCode == PasswordChangeNeeded) {
            openPage(
                pageId = NavigationConstants.NAVIGATE_TO_CHANGE_PASSWORD,
                args = data?.extras?.getBundle(ARG_LOGIN_TARGET_PAGE_DATA) ?: Bundle.EMPTY
            )
        }
        pendingNavigationRequest = null
    }
}
