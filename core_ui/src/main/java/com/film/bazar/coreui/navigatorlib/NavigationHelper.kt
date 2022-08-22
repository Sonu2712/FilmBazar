package com.film.bazar.coreui.navigatorlib

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.film.annotations.ActivityScoped
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Try and migrate all the naviagtion logic within this helper
 * e.g - navigateTo
 */
@ActivityScoped
class NavigationHelper @Inject constructor(
    activity: FragmentActivity,
    private val navigator: Navigator,
    private val navigationCallback: NavigationCallback
) {
  private val fragmentBackStack: Stack<FragmentNavigationEntry> = Stack()
  private val containerViewId: Int = navigator.getContainerViewId()
  internal val fragmentManager: FragmentManager = activity.supportFragmentManager

  val currentFragment: Fragment?
    get() = getCurrentFragment(fragmentManager, containerViewId)

  fun onBackPressed(): Boolean {
    val currentFragment = currentFragment
    return currentFragment is NavigationElement
        && currentFragment.onBackPressed()
  }

  fun canGoBack(): Boolean {
    return canGoBack(fragmentManager, containerViewId)
  }

  fun canGoBackOnStack(): Boolean {
    return fragmentBackStack.size > 1
  }

  fun goBack(): Boolean {
    return if (canGoBackOnStack()) {
      fragmentBackStack.pop()
      addFragment(fragmentBackStack.peek())
      true
    } else {
      false
    }
  }

  fun clearStack() {
    if (fragmentBackStack.isNotEmpty()) {
      fragmentBackStack.clear()
    }
  }

  fun setMainStack() {
    if (fragmentBackStack.isNotEmpty()) {
      val entry = fragmentBackStack.peek()
      fragmentBackStack.clear()
      fragmentBackStack.push(entry)
    }
  }

  internal fun addFragment(navigationEntry: FragmentNavigationEntry) {
    if (fragmentManager.isDestroyed) {
      return
    }
    val fragment = navigator.getFragment(navigationEntry.pageId) ?: return
    if (navigationEntry.checkStack && isOnBackStack(fragment)) {
      goBackOnce(null)
      return
    }
    var isSensitive = false
    val arguments = fragment.arguments
    if (arguments == null) {
      fragment.arguments = navigationEntry.args
    } else {
      arguments.putAll(navigationEntry.args)
      isSensitive = arguments.getBoolean(PAGE_IS_SENSITIVE)
      arguments.remove(PAGE_IS_SENSITIVE)
    }
    if (!navigationEntry.isAddToStack) {
      addOnStack(navigationEntry)
    }
    if (isSensitive) {
      navigator.authorizationDialog { authorizationState ->
          if (authorizationState === AuthorizationState.OK) {
              addFragment(fragment, navigationEntry.isAddToStack, navigationEntry.forceReload)
          } else {
            navigator.hideProgress()
          }
      }
    } else {
      addFragment(fragment, navigationEntry.isAddToStack, navigationEntry.forceReload)
    }
  }

  private fun addOnStack(entry: FragmentNavigationEntry) {
    if (fragmentBackStack.contains(entry)) {
      trimStackToPage(entry)
    } else {
      fragmentBackStack.push(entry)
    }
  }

  private fun trimStackToPage(entry: FragmentNavigationEntry) {
    val indexOf = fragmentBackStack.indexOf(entry)
    val currentStack = LinkedList(fragmentBackStack)
    val stackEntryCount = currentStack.size - 1
    for (i in stackEntryCount downTo 0) {
      if (i > indexOf) {
        fragmentBackStack.pop()
      }
    }
  }

  protected fun Fragment.isOnTop(): Boolean {
    val currOpenFragment = currentFragment
    return currOpenFragment != null && currOpenFragment.javaClass
        .canonicalName == javaClass.canonicalName
  }

  internal fun addFragment(newFragment: Fragment?, addStack: Boolean, forceReload: Boolean) {
    newFragment ?: return
    val containerViewId: Int = containerViewId
    val fragmentManager = this.fragmentManager
    if (fragmentManager.isDestroyed) {
      return
    }

    if (newFragment !is NavigationElement) {
      throw IllegalArgumentException("${newFragment.javaClass.canonicalName} must implement NavigationElement")
    }
    val currOpenFragment = currentFragment
    val isOnTop = newFragment.isOnTop()
    if (!forceReload && isOnTop) {
      if (currOpenFragment is NavigationElement) {
        (currOpenFragment).onFragmentReopened(newFragment.arguments ?: Bundle())
      }
      Timber.d("Already on the same page")
      return
    }

    if (currOpenFragment is NavigationElement) {
      currOpenFragment.onFragmentPause()
    }

    navigationCallback.onPageOpened(newFragment, addStack)

    val fragmentTransaction = fragmentManager.beginTransaction()
    val tag = newFragment.javaClass.canonicalName
    if (newFragment.isAdded) {
      fragmentTransaction.show(newFragment)
    } else {
      if (isOnTop && forceReload && addStack) {
        fragmentManager.popBackStackImmediate()
        fragmentTransaction.add(containerViewId, newFragment, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
      } else if (addStack && currOpenFragment != null) {
        fragmentTransaction.add(containerViewId, newFragment, tag)
            .hide(currOpenFragment)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
      } else {
        navigator.clearTitles()
        if (fragmentManager.fragments.isEmpty()) {
          fragmentTransaction.add(containerViewId, newFragment, tag)
              .commitNowAllowingStateLoss()
        } else {
          if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
          }
          fragmentTransaction.replace(containerViewId, newFragment, tag)
              .commitNowAllowingStateLoss()
        }
      }
    }
    if (!addStack) (newFragment as NavigationElement).onFragmentResume()
  }

  private fun isOnBackStack(newFragment: Fragment): Boolean {
    val backStackEntryCount = fragmentManager.backStackEntryCount
    if (backStackEntryCount > 1) {
      // Get the second last fragment tag from FragmentManager.BackStackEntry
      val backEntry = fragmentManager.getBackStackEntryAt(backStackEntryCount - 2)
      val previousFragment = fragmentManager.findFragmentByTag(backEntry.name) ?: false

      return previousFragment.javaClass.canonicalName == newFragment.javaClass.canonicalName
    }
    return false
  }

  fun goBackOnce(results: Bundle?) {
    val fragmentManager = this.fragmentManager
    if (fragmentManager.isDestroyed) {
      return
    }
    val backStackEntryCount = fragmentManager.backStackEntryCount
    if (backStackEntryCount > 0) {
      // Get the last fragment tag from FragmentManager.BackStackEntry
      val backEntry = fragmentManager.getBackStackEntryAt(backStackEntryCount - 1)
      // Closing fragment before calling popBackStackImmediate()
      val closingFragment = fragmentManager.findFragmentByTag(backEntry.name)
      if (closingFragment is NavigationElement) {
        (closingFragment as NavigationElement).onFragmentPause()
      }
      // Close the current fragment
      fragmentManager.popBackStackImmediate()
      navigationCallback.onBackNavigation()
      val currentFragment = currentFragment
      if (currentFragment != null) {
        navigator.setAppTitle(currentFragment.toString(), null)
      }
      if (currentFragment is NavigationElement) {
        if (results != null) {
          currentFragment.setFragmentResult(results)
        }
        currentFragment.onFragmentResume()
      }
    }
  }

  companion object {
    const val PAGE_IS_SENSITIVE = "isSensitive"

    @JvmStatic
    fun canGoBack(fragmentManager: FragmentManager, containerViewId: Int): Boolean {
      val backStackEntryCount = fragmentManager.backStackEntryCount
      if (backStackEntryCount == 0) {
        return false
      }
      val currentFragment = getCurrentFragment(fragmentManager, containerViewId)
      return (backStackEntryCount > 0
          && currentFragment is NavigationElement
          && currentFragment.isBackPressEnabled())
    }

    @JvmStatic
    fun getCurrentFragment(
        fragmentManager: FragmentManager,
        @IdRes container: Int
    ): Fragment? {
      val backStackEntryCount = fragmentManager.backStackEntryCount
      return if (backStackEntryCount > 0) {
        // Get the last fragment in back task after
        // popBackStackImmediate() to resume the current fragment
        val backEntry = fragmentManager.getBackStackEntryAt(backStackEntryCount - 1)
        fragmentManager.findFragmentByTag(backEntry.name)
      } else {
        // Last fragment is not attached to back stack. So get
        // the fragment by id and resume the fragment.
        fragmentManager.findFragmentById(container)
      }
    }
  }
}
