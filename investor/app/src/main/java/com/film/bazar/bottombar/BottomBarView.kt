package com.film.bazar.bottombar

import android.content.SharedPreferences
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.film.annotations.ActivityScoped
import com.film.app.core.rx.applyUiModel
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.drawermenu.data.AppMenu
import com.film.bazar.util.upcomingUserChanges
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding4.material.itemSelections
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class BottomBarView @Inject constructor(
    protected val userManager: UserManager,
    protected val preferences: SharedPreferences,
    protected val menuRepository: MenuDataSourceRepository
) : BottomBarHelper, Disposable {
    private val disposable: CompositeDisposable = CompositeDisposable()
    private lateinit var bottomBar: BottomNavigationView

    fun setView(view: BottomNavigationView) {
        this.bottomBar = view
    }

    fun start() {
        refreshBottomBar()

        userManager.upcomingUserChanges(AndroidSchedulers.mainThread())
            .subscribe { event ->
                refreshBottomBar()
            }
            .addTo(disposable)

    }

    fun onMenuSelected(): Observable<MenuItem> {
        return bottomBar.itemSelections()
    }

    override fun pageOpened(fragment: Fragment) {
        bottomBar.selectBottomMenuForPage(fragment)
    }

    fun selectMenu(menu: AppMenu) {
        val menuItem = bottomBar.menu.findItem(menu.pageId.bottomBarIntMapper())
        if (menuItem != null) {
            menuItem.isChecked = true
        } else {
            bottomBar.clearSelection()
        }
    }

    fun toggleVisibility(show: Boolean) {
        bottomBar.isVisible = show
    }

    protected fun refreshBottomBar() {
        if (userManager.isLoggedIn()) {
            val context = this.bottomBar.context
            val startupScreen = userManager.getUser()?.userConfig?.userSettings?.startupScreen
            val startupPageScreen =
                if (startupScreen == "portfolio" || startupScreen == "portfoliodayzero")
                    if (userManager.getUser()?.userConfig?.hasPortfolio == true) "portfolio" else "portfoliodayzero"
                else startupScreen
            val startupPageId =
                if (startupPageScreen.isNullOrEmpty()) "home"
                else startupPageScreen
            menuRepository.getMenuItems()
                .compose(applyUiModel())
                .subscribe {
                    it.onFailure { Timber.e("it") }
                    it.onSuccess { appMenu ->
                        val optionalSelectedMenu =
                            preferences.getString("", "")
                        val primaryMenu =
                            userManager.getUser()?.userConfig?.userSettings?.bottomMenu
                                ?: emptyList()
                        val bottomMenu = if (primaryMenu.isNullOrEmpty()) appMenu.bottomMenu
                        else appMenu.bottomMenu.filter { data1 ->
                            primaryMenu.any { data1.id == it }
                        }

                        bottomBar.setBottomBarItems(
                            if (optionalSelectedMenu.isNullOrEmpty()) emptyList() else bottomMenu.filter { !it.isDefault }
                                .map { it.id },
                            context,
                            bottomMenu,
                            startupPageId,
                            userManager.getUser()?.userConfig?.hasPortfolio ?: false
                        )
                    }
                }.addTo(disposable)
        }
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }

    override fun dispose() {
        disposable.dispose()
    }
}
