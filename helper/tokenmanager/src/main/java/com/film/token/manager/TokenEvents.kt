package com.film.token.manager

sealed class AccessToken {
  object OnRefresh : AccessToken()
  object OnUnauthorized : AccessToken()
  object OnExpire : AccessToken()
  object OnApiCall : AccessToken()
}

sealed class TokenEvents {
  object Logout : TokenEvents()
  object ReInitiate : TokenEvents()
}

object GetAccessToken : TokenEvents()
object GetRefreshToken : TokenEvents()