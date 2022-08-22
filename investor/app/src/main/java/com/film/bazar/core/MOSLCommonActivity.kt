package com.film.bazar.core

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.View.OnClickListener
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.film.annotations.AppMenu
import com.film.annotations.AppShortCutId
import com.film.annotations.FcmToken
import com.film.annotations.PreLogin
import com.film.app.core.network.NetworkChangeEvent
import com.film.app.core.network.getNetworkInfo
import com.film.app.core.prefs.BooleanPreference
import com.film.app.core.prefs.StringPreference
import com.film.app.core.rx.applyUiModel
import com.film.bazar.coreui.appcoreui.base.ActivityDelegate
import com.film.bazar.coreui.appcoreui.base.BaseActivity
import com.film.bazar.coreui.appcoreui.base.NavigationElementCore
import com.film.bazar.coreui.appcoreui.dialog.showDialogForConfirmation
import com.film.bazar.coreui.appcoreui.dialog.showToastForSuccess
import com.film.bazar.coreui.appcoreui.dialog.showToastForWarning
import com.film.bazar.MOSLApplication
import com.film.bazar.R
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.bottombar.BottomBarView
import com.film.bazar.bottombar.bottomBarStringMapper
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_LOGOUT
import com.film.bazar.coredata.CommonBaseUrls
import com.film.bazar.coreui.LocaleManager
import com.film.bazar.coreui.core.CoachMarksHelper
import com.film.bazar.coreui.core.ContainerChild
import com.film.bazar.coreui.core.ContainerManager
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.navigator.UrlNavigator
import com.film.bazar.coreui.navigatorlib.*
import com.film.bazar.dashboard.ActivityNavigationHelper
import com.film.bazar.databinding.ActivityCommonbaseBinding
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.menu.ScreenNavigatorImpl
import com.film.commons.data.onSuccess
import com.film.login.data.LoginRequested
import com.film.login.data.LoginSuccess
import com.film.login.data.MarketsEquityRequested
import com.film.login.data.PasswordChangeNeeded
import com.film.login.data.repository.LoginRepository
import com.film.login_ui.nav.LoginConstants
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jakewharton.rxbinding4.appcompat.navigationClicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import mosl.rx_preferences.Preference
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MOSLCommonActivity : BaseActivity(), ContainerManager, ActivityDelegate, CoachMarksHelper {
    @Inject
    lateinit var fragmentResolver: FragmentResolver

    @Inject
    @FcmToken
    lateinit var tokenPref: Preference<String>

    @Inject
    @PreLogin
    lateinit var preLogin: BooleanPreference

    lateinit var binding: ActivityCommonbaseBinding
    internal var snackbar: Snackbar? = null
    private var doubleBackToExitPressedOnce = false

    @Inject
    lateinit var navigationHelper: NavigationHelper
    internal val currentFragment: Fragment?
        get() = navigationHelper.currentFragment

    @Inject
    lateinit var screenNavigator: ScreenNavigatorImpl

    @Inject
    lateinit var activityNavHelper: ActivityNavigationHelper

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var bottomBarView: BottomBarView

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    var networkEvent: NetworkChangeEvent? = null

    @Inject
    lateinit var application: MOSLApplication

    @Inject
    lateinit var baseUrls: CommonBaseUrls

    @Inject
    lateinit var menuDataSourceRepository: MenuDataSourceRepository

    @Inject
    lateinit var urlNavigator: UrlNavigator

    @Inject
    @AppShortCutId
    lateinit var appShortCutId: StringPreference

    @Inject
    @AppMenu
    lateinit var appMenuPref: StringPreference

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManager.withLocale(newBase))
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        super.applyOverrideConfiguration(baseContext.resources.configuration)
    }

    override fun getLayout(): Int {
        return R.layout.activity_commonbase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("OnCreate Called")
        binding = ActivityCommonbaseBinding.bind(findViewById(R.id.drawer_layout))
        binding.toolBar.title = ""
        setSupportActionBar(binding.toolBar)
        binding.toolBar.setNavigationMode(goBack = false)
        setupBottomBar()
        if (savedInstanceState == null) {
            val uniqueId = intent.data.getUniqueId()
            if (uniqueId.isNotEmpty())
                handlePlaceOrderDynamicDeepLink(uniqueId)
            else {
                activityNavHelper.loadIntent(intent)
            }
        }

        setupObservables()
        fetchUserIndices()
        /* if (userManager.getUserType().isCustomer) {
             fetchUserConfigs(false)
         }*/
        Handler().postDelayed(Runnable {
            val currFrag = currentFragment ?: return@Runnable
            val currFragTag = currFrag.toString()
            bottomBarView.pageOpened(currFrag)
            (currFrag as? ContainerChild)?.let {
                setState(currFragTag, it.containerState)
            }
        }, 1000)
    }

    private fun handlePlaceOrderDynamicDeepLink(uniqueId: String) {
        screenNavigator.openHome()
    }

    private fun Uri?.getUniqueId(default: String = ""): String {
        this ?: return default
        return if (lastPathSegment.equals("orderlink")) {
            getQueryParameter("uniqueid") ?: default
        } else
            default
    }

    private fun fetchUserIndices() {
        if (userManager.isLoggedIn()) {
            //indicesRepository.getUserIndianIndices().process()
        }
    }

    private fun setupObservables() {
        val mainThread = dispatchers.main
        userManager.onUserChanged()
            .distinctUntilChanged()
            .skip(1)
            .observeOn(mainThread)
            .subscribe { event ->
                // hideProgress()
                fetchUserIndices()
                /*if (userManager.getUserType().isCustomer) {
                    fetchUserConfigs(true)
                }*/
                Handler().postDelayed({
                    if (appShortCutId.get() != LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN)
                        screenNavigator.openHome()
                }, 500)
            }.addTo(disposable)

        binding.toolBar.navigationClicks()
            .map { binding.toolBar.isBackNavigation() }
            .subscribe { goBack ->
                if (goBack) {
                    if (navigationHelper.onBackPressed()) {
                        //Empty case necessary
                    } else {
                        screenNavigator.goBack()
                    }
                } else {
                    openMenuDrawer()
                }
            }
            .addTo(disposable)
    }

    fun logoutClicked(){
        showDialogForConfirmation(
            getString(R.string.app_msg_logout_alert),
            negativeButton = getString(R.string.button_label_cancel),
            cancelable = false
        ) { positiveClicked ->
            if (positiveClicked) {
                doLogout()
            }
        }
    }

    fun doLogout() {
        loginRepository
            .logoutUser()
            .toSingleDefault(true)
            .toObservable()
            .doOnNext { Timber.i("User Logged Out") }
            .compose(applyUiModel())
            .subscribe {
                binding.progressBar.visibility = if (it.inProgress) View.VISIBLE else View.GONE
                it.onSuccess {
                    this.showToastForSuccess(getString(R.string.app_msg_log_out_successful))
                    binding.drawerLayout.closeDrawers()
                }
            }.addTo(disposable)
    }

    private fun openMenuDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun authorizationDialog(listener: (AuthorizationState) -> Unit) {
    }

    override fun clearTitles() {
    }

    fun onEvent(event: NetworkChangeEvent) {
        networkEvent = event
        val networkInfo = this.getNetworkInfo()
        Timber.i("Network Changed : $event, Network Info : $networkInfo")

        val currentFragment = currentFragment
        if (currentFragment is NavigationElementCore) {
            (currentFragment as NavigationElementCore).networkChanged(event)
        }

        if (event == NetworkChangeEvent.Connected) {
            snackbar?.dismiss()
        } else {
            snackbar = showNetworkDisconnectedSnackbar()
        }
    }

    internal fun showNetworkDisconnectedSnackbar(): Snackbar {
        val snackbar = Snackbar.make(
            binding.coordinatorLayout, R.string.app_network_unavailable,
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(R.string.button_label_ok) { v -> snackbar.dismiss() }
        snackbar.show()
        return snackbar
    }

    private fun setupBottomBar() {
        bottomBarView.setView(binding.bottomNavigation)
        bottomBarView.onMenuSelected()
            .subscribe {
                val pageId = it.itemId.bottomBarStringMapper()
                if (pageId == NavigationConstants.NAVIGATE_TO_MORE){
                    logoutClicked()
                } else screenNavigator.openPage(pageId = pageId)
            }.addTo(disposable)
        bottomBarView.start()
    }

    override fun getContainerViewId(): Int {
        return R.id.activityFrameLayout
    }

    override fun getFragment(pageId: String): Fragment? {
        Timber.d("Get Fragment : $pageId")
        val fragment = fragmentResolver.resolve(pageId)
        preferences.edit().putString(fragment?.javaClass?.canonicalName.toString(), pageId).apply()
        return fragment
    }

    override fun gotoPage(fragmentEntry: FragmentNavigationEntry) {
        fragTransitionHelper.navigateTo(fragmentEntry)
    }

    fun getAppTitle(appTitle: AppTitle): String {
        return if (appTitle.hasTitle()) {
            if (appTitle.hasStringRes()) {
                getString(appTitle.title).toUpperCase(Locale.ENGLISH)

            } else {
                appTitle.titleStr!!.toUpperCase(Locale.ENGLISH)
            }
        } else {
            ""
        }
    }

    override fun setState(page: String, state: ContainerState) {
        if (state.showToolbar) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
        bottomBarView.toggleVisibility(state.showBottomBar)
        binding.toolBar.setNavigationMode(state.showBackButton, state.showNavigationIcon)
        binding.toolBar.setCustomView(state.toolbarView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.appBar.elevation = if (state.showShadow) {
                0f
            } else 0f
        }
        setTitleAndOverflow(state.appTitle)
        window.setSoftInputMode(
            if (state.showBottomBar) WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            else WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun onBackNavigation() {
        val currFrag = currentFragment ?: return
        val currFragTag = currFrag.toString()
        bottomBarView.pageOpened(currFrag)
        (currFrag as? ContainerChild)?.let {
            setState(currFragTag, it.containerState)
        }
    }

    override fun hideAppTitle() {
        binding.toolBar.title = ""
    }

    override fun setAppTitle(page: String, appTitle: AppTitle?) {
        Timber.d("ActionBarNew Set Title for %s, to %s", page, title)
        //error("This should not be called, Use setTitle() instead of setAppTitle()")
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    protected fun setTitleAndOverflow(appTitle: AppTitle) {
        binding.toolBar.setAppTitle(appTitle)
    }

    override fun isConnected(): Boolean {
        return networkEvent == NetworkChangeEvent.Connected
    }

    override fun hideOverflowMenu() {
    }

    override fun selectMenu(label: Int) {
    }

    override fun setOverflowClickListener(listener: OnClickListener?) {
    }

    override fun showOverflowMenu() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Timber.i("IS PLace Order ${intent.data}")
        val uniqueId = intent.data.getUniqueId()
        if (uniqueId.isNotEmpty())
            handlePlaceOrderDynamicDeepLink(uniqueId)
        else {
            activityNavHelper.loadIntent(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        screenNavigator.onActivityResult(requestCode, resultCode, data)

        if (resultCode == LoginSuccess) {
            setResult(LoginSuccess)
        }

        //To prevent user to going to any pre-login screen
        if (requestCode == LoginRequested
            && resultCode != LoginSuccess
            && resultCode != PasswordChangeNeeded
            && !preLogin.get()
        ) {
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
            return
        }

        if (binding.progressBar.visibility == View.VISIBLE) {
            binding.progressBar.visibility = View.GONE
            return
        }

        if (navigationHelper.onBackPressed()) {
            return
        }

        if (navigationHelper.canGoBack()) {
            screenNavigator.goBack()
            return
        }

        // exitApplication()
        handleExitAppPopup()
    }

    private fun handleExitAppPopup() {
        exitApplication()
    }

    private fun exitApplication() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }

        doubleBackToExitPressedOnce = true
        this.showToastForWarning(
            message = getString(R.string.app_msg_click_again_to_exit),
            isBackPress = true
        )
        disposable.add(
            Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe { doubleBackToExitPressedOnce = false })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
        appMenuPref.delete()
        bottomBarView.dispose()
        preferences.edit().remove(ARG_FROM_CHART_ACTIVITY).apply()
    }

    companion object {
        const val ARG_PAGE_ID = "pageId"
        const val ARG_FROM_CHART_ACTIVITY = "fromChartActivity"
    }

    override fun getRootView(): View {
        return binding.root
    }
}