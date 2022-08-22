package com.film.bazar.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.film.bazar.R
import com.google.gson.Gson
import com.film.app.core.network.isNetworkConnected
import com.film.app.core.prefs.StringPreference
import com.film.app.core.rx.applyUiModel
import com.film.app.core.utils.showConfirmation
import com.film.bazar.coreui.appcoreui.base.LeanBaseActivity
import com.film.commons.data.onSuccess
import com.film.login.data.repository.LoginRepositoryImpl
import com.film.login_ui.nav.LoginConstants
import com.film.annotations.AppShortCutId
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.core.MOSLCommonActivity
import com.film.bazar.coreui.LocaleManager
import com.film.bazar.databinding.ActivitySplashScreenBinding
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.util.RootDeviceHelper.isEmulator
import com.film.bazar.util.RootDeviceHelper.isRooted
import com.film.bazar.appusercore.model.User
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class SplashActivity : LeanBaseActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var loginRepository: LoginRepositoryImpl

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    @AppShortCutId
    lateinit var appShortCutId: StringPreference

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var menuDataSourceRepository: MenuDataSourceRepository

    private val menuSubject = PublishSubject.create<User>()

    override fun getLayout(): Int {
        return R.layout.activity_splash_screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.bind(findViewById(R.id.splash_root))
    }

    override fun clearWindowBackground(): Boolean {
        return false
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.withLocale(base))
    }

    private fun checkNetwork() {
        if (this.isNetworkConnected()) {
            if (!isEmulator() && isRooted()) {
                showRootedMessage()
            } else {
                initializeApp()
                setupMenuObservable()
            }
        } else {
            noNetworkAlert()
        }
    }

    private fun setupMenuObservable() {
        menuSubject.switchMap {
            menuDataSourceRepository.getAllAppMenu(it.userType.userTypeString)
                .compose(applyUiModel())
        }.subscribe {
            it.onSuccess { appMenus ->
                menuDataSourceRepository.saveAppMenusLocal(appMenus)
            }
        }.addTo(disposable)
    }

    private fun showRootedMessage() {
        actionableAlert(
            message = getString(R.string.app_text_alert_rooted_device),
            cancelable = false,
            buttonText = R.string.app_text_alert_exit_app
        ) {
            finish()
        }
    }

    private fun initializeApp() {
        when {
            userManager.isLoggedIn() -> verifyLogin()
            else -> openLogin()
        }
    }

    private fun verifyLogin() {
        menuSubject.onNext(userManager.getUser())
        openDashboard()
    }

    internal fun openDashboard() {
        val intent = Intent(this, MOSLCommonActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openLogin() {
        val login = Intent(this, MOSLCommonActivity::class.java).apply {
            putExtra(MOSLCommonActivity.ARG_PAGE_ID, LoginConstants.NAVIGATE_TO_LOGIN_BASE)
        }
        startActivity(login)
    }

    private fun noNetworkAlert() {
        showConfirmation(
            message = R.string.app_text_alert_no_network_alert,
            cancelable = false,
            positiveButton = R.string.app_text_alert_check_network,
            negativeButton = R.string.app_text_alert_exit_app
        ) { positiveClicked ->
            if (positiveClicked) {
                checkNetwork()
            } else {
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        checkNetwork()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val res = checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE);
            if (res != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    REQUEST_CODE_ASK_PERMISSIONS
                )
            } else {
                checkNetwork()
            }
        } else {
            checkNetwork()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        intent.data = null
    }

    companion object {
        const val REQUEST_CODE_ASK_PERMISSIONS = 1002
    }
}
