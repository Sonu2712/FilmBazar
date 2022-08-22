package com.film.bazar.helper

import android.content.ComponentCallbacks2
import android.content.res.Configuration
import com.film.commons.cache.InMemoryCache
import com.film.bazar.appuser.repository.UserManager
import okhttp3.Cache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CentralizedCache @Inject constructor(
    userManager: UserManager,
    internal val inMemoryCache: InMemoryCache,
    internal val apiCache: Cache
) : ComponentCallbacks2 {

    init {
        userManager.onUserChanged()
            .distinctUntilChanged()
            .skip(1)
            .subscribe {
                try {
                    inMemoryCache.clearUserEntries()
                    apiCache.evictAll()
                } catch (_: Throwable) {
                }
            }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // no-op
    }

    override fun onTrimMemory(level: Int) {
        // no-op
    }

    override fun onLowMemory() {
        clearMemoryCache()
    }

    fun clearMemoryCache() {
        try {
            inMemoryCache.evictAll()
        } catch (_: Exception) {
        }
    }
}
