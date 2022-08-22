package com.film.bazar.helper

import androidx.appcompat.app.AppCompatActivity
import com.film.annotations.ActivityScoped
import com.film.app.core.prefs.StringPreference
import com.film.annotations.ClientCode
import com.film.bazar.appuser.repository.UserManagerImpl
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.navigatorlib.Navigator
import dagger.Lazy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

interface CustomNavigator {
    fun handle(entry: CustomNavigationEntry)
}

@ActivityScoped
class CustomNavigatorImpl @Inject constructor(
    protected val activity: AppCompatActivity,
    protected val navigator: Navigator,
    @param:ClientCode protected val clientCode: StringPreference,
    protected val screenNavigator: Lazy<ScreenNavigator>,
    protected val userManager: UserManagerImpl
) : CustomNavigator, Disposable {
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun handle(entry: CustomNavigationEntry) {
        val pageId = entry.pageId
        when (pageId) {
        }
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }

    override fun dispose() {
        disposable.dispose()
    }

}