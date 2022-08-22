package com.film.bazar.coreui.navigatorlib

import android.net.Uri
import android.os.Bundle

/**
 * Represents a Navigation that can be performed by this application
 */
abstract class NavigationEntry

/**
 * Represents a Navigation in form a url.
 * It can be a general web url or a deep link url specific to this app.
 */
data class UriNavigationEntry(
    @JvmField val uri: Uri
) : NavigationEntry() {
  constructor(uri: String) : this(uri = Uri.parse(uri))
}

/**
 * Represents a Navigation that involves Activity and its requisite data
 */
data class ActivityNavigationEntry @JvmOverloads constructor(
    @JvmField val activity: Class<*>,
    @JvmField val bundle: Bundle = Bundle.EMPTY,
    @JvmField val requestCode: Int? = null
) : NavigationEntry()

data class DialogNavigationEntry @JvmOverloads constructor(
    @JvmField val pageId: String,
    @JvmField val args: Bundle = Bundle.EMPTY
) : NavigationEntry()

/**
 * Represents a Navigation involving Fragments within any host Activity.
 * It's use should be limited to fragment transitions.
 */
data class FragmentNavigationEntry @JvmOverloads constructor(
    @JvmField val pageId: String,
    @JvmField val args: Bundle?,
    @JvmField val isAddToStack: Boolean,
    @JvmField val isGoBack: Boolean = false,
    @JvmField val forceReload: Boolean = false,
    @JvmField val checkStack: Boolean = false
) : NavigationEntry() {
  companion object {
    const val GoBackPageId = "none"
    @JvmField
    val GoBackEntry = FragmentNavigationEntry(GoBackPageId, null, false, true)

    @JvmStatic
    fun openPreviousInstance(
        pageId: String,
        args: Bundle,
        addToStack: Boolean
    ): FragmentNavigationEntry {
      return FragmentNavigationEntry(pageId, args, addToStack, false, false, true)
    }

    @JvmStatic
    fun forceReload(
        pageId: String,
        args: Bundle,
        addToStack: Boolean
    ): FragmentNavigationEntry {
      return FragmentNavigationEntry(pageId, args, addToStack, false, true)
    }

    @JvmStatic
    fun goBack(): FragmentNavigationEntry {
      return GoBackEntry
    }

    @JvmStatic
    fun goBackWithResult(args: Bundle): FragmentNavigationEntry {
      return FragmentNavigationEntry(GoBackPageId, args, false, true)
    }
  }
}
