package com.film.login_ui.core

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.film.login_ui.R
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import com.film.app.core.prefs.BooleanPreference
import com.film.bazar.coreui.appcoreui.base.ActivityDelegate
import com.film.bazar.coreui.appcoreui.base.BaseActivity
import com.film.login_ui.nav.LoginConstants
import com.film.annotations.PreLogin
import com.film.bazar.coreui.LocaleManager
import com.film.bazar.coreui.navigatorlib.*
import com.film.login_ui.databinding.ActivityLoginBinding
import java.util.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), Navigator, ActivityDelegate {
    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var navigator: LoginNavigator

    @Inject
    lateinit var navigationHelper: NavigationHelper

    @Inject
    lateinit var fragmentResolver: FragmentResolver

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    @PreLogin
    lateinit var preLogin: BooleanPreference

    private var globalTitles = HashMap<String, AppTitle>()

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.bind(
            findViewById(
                R.id.root_coordinator
            )
        )
        setSupportActionBar(binding.toolbar)

        // Following Changes are for Not Showing Arrow for First Time
        val isUserInitiated = preferences.getBoolean(IS_USER_INITIATED, false)
        val showHomeAsUp = isUserInitiated && preLogin.get()
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(showHomeAsUp)
            it.setHomeButtonEnabled(showHomeAsUp)
            it.setDisplayShowHomeEnabled(showHomeAsUp)
            it.setDisplayShowTitleEnabled(false)
        }
        preferences.edit().putBoolean(IS_USER_INITIATED, true).apply()

        if (savedInstanceState == null) {
            setupDefaultFragment(intent)
        } else {
            globalTitles =
                savedInstanceState.getSerializable(PAGE_TITLES) as HashMap<String, AppTitle>
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(PAGE_TITLES, globalTitles)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManager.withLocale(newBase))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        super.applyOverrideConfiguration(baseContext.resources.configuration)
    }

    private fun setupDefaultFragment(data: Intent) {
        val extras = data.extras ?: Bundle.EMPTY
        navigator.openPage(
            pageId = LoginConstants.NAVIGATE_TO_LOGIN_BASE,
            args = extras,
            addToStack = false
        )
    }

    override fun getContainerViewId(): Int {
        return R.id.activityFrameLayout
    }

    override fun getFragment(pageId: String): Fragment? {
        return fragmentResolver.resolve(pageId)
    }

    override fun authorizationDialog(listener: (AuthorizationState) -> Unit) {
        TODO(
            "Not Needed in Login Module"
        )
    }

    override fun setAppTitle(
        page: String,
        appTitle: AppTitle?
    ) {
        val pageTitle: AppTitle? = appTitle ?: globalTitles[page]
        if (pageTitle != null) {
            globalTitles[page] = pageTitle
            supportActionBar?.show()
            if (pageTitle.hasStringRes()) {
                setTitle(pageTitle.title)
            } else {
                title = pageTitle.titleStr
            }
        } else {
            hideAppTitle()
        }
    }

    override fun hideAppTitle() {
        supportActionBar?.hide()
        title = ""
    }

    override fun clearTitles() {
        globalTitles.clear()
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun isConnected(): Boolean {
        return true
    }

    override fun selectMenu(label: Int) {
    }

    override fun hideOverflowMenu() {}

    override fun showOverflowMenu() {}

    override fun setOverflowClickListener(listener: OnClickListener?) {}

    override fun onBackPressed() {
        if (navigationHelper.onBackPressed()) {
            return
        }

        if (navigationHelper.canGoBack()) {
            navigator.goBack()
            return
        } else {
            navigator.finish()
        }
    }

    override fun gotoPage(fragmentEntry: FragmentNavigationEntry) {
        fragTransitionHelper.navigateTo(fragmentEntry)
    }

    companion object {
        const val ARG_PAGE_ID = "pageId"
        const val ARG_LOGIN_TARGET_PAGE_ID = "targetPageId"
        const val ARG_LOGIN_TARGET_PAGE_DATA = "targetPageData"
        const val ARG_PAGE_MODE = "pageMode"
        const val PAGE_TITLES = "pageTitles"
        const val IS_USER_INITIATED = "isUserInitiated"
    }
}
