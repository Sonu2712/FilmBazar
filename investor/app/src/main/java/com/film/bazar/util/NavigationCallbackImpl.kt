package com.film.bazar.util

import androidx.fragment.app.Fragment
import com.film.annotations.ActivityScoped
import com.film.bazar.bottombar.BottomBarHelper
import com.film.bazar.coreui.core.ContainerManager
import com.film.bazar.coreui.navigatorlib.NavigationCallback
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@ActivityScoped
class NavigationCallbackImpl @Inject constructor(
    val helper: BottomBarHelper,
    val containerManager: ContainerManager
) : NavigationCallback {
    private val pageStack = ArrayList<String>()

    override fun onPageOpened(fragment: Fragment, addedToStack: Boolean) {
        setCrashlyticsInfo(fragment, addedToStack)
        helper.pageOpened(fragment)
    }

    override fun onBackNavigation() {
        removeFromStack()
        containerManager.onBackNavigation()
    }

    private fun removeFromStack() {
        if (pageStack.size > 0) {
            pageStack.removeAt(pageStack.size - 1)
        }
        Timber.i("Current Stack : %s", pageStack.toString())
    }

    private fun updatePageStack(entry: String, addStack: Boolean) {
        if (addStack) {
            pageStack.add(entry)
        } else {
            pageStack.clear()
            pageStack.add(entry)
        }
        Timber.i("Current Stack : %s", pageStack.toString())
    }

    private fun setCrashlyticsInfo(fragment: Fragment, addStack: Boolean) {
        updatePageStack(fragment.toString(), addStack)
    }
}
