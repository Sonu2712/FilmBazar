package com.film.token.manager

import android.os.SystemClock
import androidx.annotation.WorkerThread
import com.jakewharton.rxrelay3.PublishRelay
import com.film.commons.utils.Clock
import com.film.commons.utils.Logger
import com.film.token.manager.data.AccessTokenInfo
import com.film.token.manager.data.JwtTokens
import com.film.token.manager.data.TokenInfo
import com.film.token.manager.prefs.TokenPrefStore
import com.film.token.manager.prefs.getOrDefault
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject internal constructor(
    internal val prefStore: TokenPrefStore,
    internal val clock: Clock
) {
  internal val tokenRenewalSubject: PublishRelay<AccessToken> =
      PublishRelay.create()
  internal val tokenEventsSubject: PublishRelay<TokenEvents> = PublishRelay.create()
  internal val tokenProviderSubject: PublishRelay<String> = PublishRelay.create()
  internal val disposable: CompositeDisposable = CompositeDisposable()
  internal var logger: Logger = object : Logger {
    override fun log(message: String) {
      Timber.tag("TokenManager").d(message)
    }
  }
  internal val LoggerConsumer: Consumer<Any> = Consumer {
    logger.log("Event - $it")
  }

  init {
    if (wasDeviceRebooted(prefStore.tokenCreationTime.get())) {
      prefStore.loginTime.set(0)
      prefStore.tokenCreationTime.set(0)
    }
  }

  val tokens: JwtTokens
    get() {
      return JwtTokens(accessToken, refreshToken)
    }

  fun saveLoginTime(loginTime: Long) {
    prefStore.loginTime.set(loginTime)
  }

  fun saveTokens(tokenInfo: TokenInfo) {
    saveAccessToken(
        AccessTokenInfo(tokenInfo.accessToken, tokenInfo.expiresIn, clock.getTimestamp())
    )
    prefStore.refreshToken.set(tokenInfo.refreshToken)
    prefStore.reLoginDuration.set(tokenInfo.reLoginAfter * 1000L)
  }

  fun saveAccessToken(infoAccess: AccessTokenInfo) {
    prefStore.accessToken.set(infoAccess.accessToken)
    prefStore.tokenValidity.set(infoAccess.expiresIn * 1000L)
    prefStore.tokenCreationTime.set(infoAccess.loginTime)
    notifySubscribers(infoAccess.accessToken)
    tokenRenewalSubject.accept(AccessToken.OnRefresh)
  }

  private fun notifySubscribers(token: String) {
    tokenProviderSubject.accept(token)
  }

  /**
   * Returns access token, refreshes if it has expired
   */
  @WorkerThread
  fun getAuthorizationToken(): Single<String> {
    return if (isTokenExpired) {
      logger.log("Token Expired----------")
      tokenRenewalSubject.accept(AccessToken.OnApiCall)
      tokenProviderSubject.firstOrError()
    } else {
      Single.just(accessToken)
    }
  }

  val accessToken: String
    get() = prefStore.accessToken.getOrDefault("")

  val refreshToken: String
    get() = prefStore.refreshToken.getOrDefault("")

  fun hasTokens(): Boolean {
    return prefStore.accessToken.isSet && prefStore.refreshToken.isSet
  }

  fun onTokenEvent(): Observable<TokenEvents> {
    return tokenEventsSubject.hide()
  }

  fun logoutNeeded() {
    tokenEventsSubject.accept(TokenEvents.Logout)
  }

  fun refreshTokenExpired() {
    tokenEventsSubject.accept(GetRefreshToken)
  }

  fun expired() {
    tokenRenewalSubject.accept(AccessToken.OnUnauthorized)
  }

  /**
   * Check if the loginTime is not saved or Util.wasDeviceRebooted(savedTime)
   * Also, checks if MINIMUM_LOGIN_THRESHOLD has elapsed since Last Login
   *
   * @return true
   */
  fun isSilentLoginNeeded(): Boolean {
    return getSilentLoginValidity() <= 0
  }

  private fun getSilentLoginValidity(): Long {
    val loginTime = prefStore.loginTime.get()
    if (loginTime == 0L) return 0L
    val reLoginAt = loginTime + prefStore.reLoginDuration.get()
    val currentTime = clock.getTimestamp()
    return if (currentTime > reLoginAt) {
      0
    } else reLoginAt - currentTime
  }

  private val remainingValidity: Long
    get() {
      val creationTime = prefStore.tokenCreationTime.get()
      if (creationTime == 0L) return 0
      val expiresAt = creationTime + prefStore.tokenValidity.get() - TOKEN_SYNC_BUFFER_TIME
      val currentTime = clock.getTimestamp()
      return if (currentTime > expiresAt) {
        0
      } else expiresAt - currentTime
    }

  val isTokenExpired: Boolean
    get() = remainingValidity <= 0

  fun start() {
    stop()
    logger.log("Starting TokenManager")
    val interval = Observable.interval(0, 30, SECONDS)
    val tokenRenewalObs = tokenRenewalSubject.hide().share()

    disposable.add(
        interval.subscribe {
          if (isSilentLoginNeeded()) {
            tokenEventsSubject.accept(GetRefreshToken)
          } else if (isTokenExpired) {
            tokenRenewalSubject.accept(AccessToken.OnExpire)
          }
        })

    val onRefreshObs = tokenRenewalObs.filter { it === AccessToken.OnRefresh }
        .delay {
          Observable.timer(remainingValidity, MILLISECONDS)
        }

    val onExpiredObs = tokenRenewalObs
        .filter { it === AccessToken.OnExpire }

    val onUnauthorizedObs = tokenRenewalObs.filter { it === AccessToken.OnUnauthorized }

    val onApiCallObs = tokenRenewalObs.filter { it === AccessToken.OnApiCall }

    disposable.add(
        Observable.merge(onRefreshObs, onExpiredObs, onUnauthorizedObs, onApiCallObs)
            .doOnNext(LoggerConsumer)
            .map { it }
            .debounce(2000L, MILLISECONDS)
            .doOnNext { logger.log("Call /token Event") }
            .distinctUntilChanged()
            .subscribe(
                { tokenEventsSubject.accept(GetAccessToken) },
                { Timber.e(it, "TokenManagerSequence Failed") })
    )
  }

  fun stop() {
    logger.log("Stopping TokenManager")
    disposable.clear()
  }

  fun clear() {
    stop()
    prefStore.clear()
  }

  companion object {
    internal val TOKEN_SYNC_BUFFER_TIME = TimeUnit.MINUTES.toMillis(1)
    internal fun wasDeviceRebooted(savedTime: Long): Boolean {
      val realTime = SystemClock.elapsedRealtime()
      return savedTime >= realTime
    }
  }
}
