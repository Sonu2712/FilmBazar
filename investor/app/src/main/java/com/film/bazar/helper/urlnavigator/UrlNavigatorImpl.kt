package com.film.bazar.helper.urlnavigator

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.film.annotations.ActivityScoped
import com.film.login_ui.nav.LoginConstants
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.helper.isDeepLink
import com.film.bazar.coreui.helper.isEkycDeepLink
import com.film.bazar.coreui.helper.isPinkAssit
import com.film.bazar.coreui.helper.isWebLink
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.navigator.UrlNavigator
import com.film.bazar.helper.CustomNavigationEntry
import com.film.bazar.helper.CustomNavigator
import com.film.bazar.helper.OpenAccountDeepLinkNavigationEntry
import com.film.bazar.helper.OpenAccountNavigationEntry
import com.film.bazar.coreui.navigatorlib.NavigationHelper
import com.film.bazar.coreui.navigatorlib.NavigationRequest
import com.film.bazar.coreui.navigatorlib.UriNavigationEntry
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class UrlNavigatorImpl @Inject constructor(
    protected val navigationHelper: NavigationHelper,
    protected val screenNavigator: ScreenNavigator,
    protected val customNavigator: CustomNavigator,
    protected val activity: AppCompatActivity,
    protected val userManager: UserManager
) : UrlNavigator {

    // Used for internal navigation & external urls
    override fun openUrl(url: String, addToBackStack: Boolean) {
        Timber.i("Url Received for Internal Navigation : $url")
        if (url.isEmpty()) return
        val uri = Uri.parse(url)
        val entry = when {
            url.isWebLink() -> {
                uri.urlNavigationRequest()
            }
            url.isPinkAssit() -> {
                uri.internalNavigationRequest(true, addToBackStack)
            }
            else -> {
                uri.internalNavigationRequest(addToBackStack = addToBackStack)
            }
        }
        handleNavigationRequest(entry)
    }

    // Used only for notification actions & common activity intents
    override fun openUri(uri: Uri) {
        Timber.i("Url Received for External Navigation : $uri")
        val isDeepLink = uri.isDeepLink()
        val isEkycDeepLinkUrl = uri.isEkycDeepLink()
        val entry = when {
            isDeepLink -> uri.parseDeepLink()
            isEkycDeepLinkUrl -> uri.parseDeepLink()
            else -> uri.urlNavigationRequest()
        }
        handleNavigationRequest(entry)
    }


    private fun Uri.parseDeepLink(): NavigationRequest {
//        Timber.d("Last Path Segment :$lastPathSegment")
        val request = when (lastPathSegment) {

            LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT -> {
                NavigationRequest(
                    pageId = lastPathSegment!!,
                    data = OpenAccountNavigationEntry
                )
            }

            else -> {
                internalNavigationRequest(addToBackStack = true)
            }
        }

        return request ?: internalNavigationRequest(addToBackStack = true)
    }

    private fun Uri.getDeeplinkQueryParameters(pageId: String): NavigationRequest {
        val parameters = queryParameterNames
        var parameterMap = HashMap<String, String>()
        if (parameters.isNotEmpty()) {
            for (parameter in query?.split("&")!!) {
                var list = parameter.split("=")
                if (list.get(0).contains("utm_", true))
                    parameterMap.put(list.get(0), list.get(1))
            }
        }
        return NavigationRequest(
            pageId,
            data = CustomNavigationEntry(pageId, data = parameterMap)
        )
    }

    private fun Uri.getDeeplinkQueryParametersForSmallCasePage(pageId: String): NavigationRequest {
        val parameters = queryParameterNames
        var parameterMap = HashMap<String, String>()
        if (parameters.isNotEmpty()) {
            if (!getQueryParameter("path").isNullOrBlank()) {
                parameterMap.put("path", getQueryParameter("path").toString())
                for (parameter in query?.split("&")!!) {
                    var list = parameter.split("=")
                    if (list.get(0).contains("utm_", true))
                        parameterMap.put(list.get(0), list.get(1))
                }
            }
        }
        return NavigationRequest(
            pageId,
            data = CustomNavigationEntry(pageId, data = parameterMap)
        )
    }

    private fun handleNavigationRequest(request: NavigationRequest) {

        when (val data = request.data) {

            is UriNavigationEntry -> {
                screenNavigator.openPage(data)
            }

            is CustomNavigationEntry -> {
                openHomePageIfNecessary()
                customNavigator.handle(data)
            }

            is OpenAccountNavigationEntry -> {
            }
            else -> {
                screenNavigator.openPage(request)
            }
        }
    }

    /**
     * Open default fragment if currentFragment is null.
     */
    private fun openHomePageIfNecessary() {
        if (navigationHelper.currentFragment == null) {
            Timber.i("Opening home")
            screenNavigator.openHome()
        }
    }
}

private const val DefaultSource = "DeepLink"
private const val pinkAssistSource = "pinkAssistId"

private fun Uri.urlNavigationRequest(source: String = DefaultSource): NavigationRequest {
    return NavigationRequest(
        pageId = NavigationConstants.NAVIGATE_TO_URL,
        data = UriNavigationEntry(this),
        source = source
    )
}

private fun Uri.internalNavigationRequest(
    isPinkAssist: Boolean = false,
    addToBackStack: Boolean
): NavigationRequest {
    if (isPinkAssist) {
        return NavigationRequest(
            pageId = lastPathSegment!!,
            bundle = Bundle().apply {
                getQueryParameter(pinkAssistSource)?.let { putInt(pinkAssistSource, it.toInt()) }
            },
            addToStack = addToBackStack
        )

    } else
        return NavigationRequest(
            pageId = lastPathSegment!!,
            bundle = Bundle.EMPTY,
            addToStack = addToBackStack
        )
}