package com.film.bazar.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.film.annotations.ActivityScoped
import com.film.app.core.prefs.StringPreference
import com.film.annotations.AppShortCutId
import com.film.annotations.ClientCode
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.coredata.CommonBaseUrls
import com.film.bazar.coreui.navigatorlib.*
import com.film.bazar.helper.CustomNavigator
import com.film.login_ui.core.LoginActivity
import javax.inject.Inject

@ActivityScoped
class ScreenNavigatorImpl @Inject constructor(
    activity: AppCompatActivity,
    userManager: UserManager,
    navigationHelper: NavigationHelper,
    @param:ClientCode protected val cCode: StringPreference,
    @param:AppShortCutId protected val appShortCutId: StringPreference,
    customNavigator: CustomNavigator,
    navigator: Navigator,
    baseUrls: CommonBaseUrls
) : ScreenNavigatorInternal(
    activity, userManager, navigationHelper, cCode,
    customNavigator, navigator,baseUrls
) {
    var netcoreEventCount = 0
    override fun goBack() {
        navigator.gotoPage(FragmentNavigationEntry.GoBackEntry)
    }

    override fun goBack(result: Bundle) {
        processNavigation(FragmentNavigationEntry.goBackWithResult(result))
    }

    override fun openHome() {
        if (userManager.getUserType().isCustomer) {
            openPage("home")
        } else {
            activity.showAppIntro()
        }
        appShortCutId.delete()
    }

    private fun AppCompatActivity.showAppIntro() {
        val appIntroActivity = Intent(this, LoginActivity::class.java)
        startActivity(appIntroActivity)
        finish()
    }

    override fun openPage(entry: NavigationEntry) {
        processNavigation(entry)
    }

    override fun openPage(
        pageId: String,
        args: Bundle,
        addToStack: Boolean,
        forceReload: Boolean
    ) {
        openPage(NavigationRequest(pageId, args, addToStack, forceReload))
    }

    override fun openPage(pageId: String, addToStack: Boolean) {
        openPage(pageId, Bundle.EMPTY, addToStack)
    }

    override fun pageNotAccessible() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}