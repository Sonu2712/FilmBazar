package com.film.token.manager.data

data class TokenInfo(
    @JvmField val accessToken: String,
    @JvmField val refreshToken: String,
    @JvmField val expiresIn: Int,
    @JvmField val reLoginAfter: Int
)

data class AccessTokenInfo(
    @JvmField val accessToken: String,
    @JvmField val expiresIn: Int,
    @JvmField val loginTime: Long
)

data class JwtTokens(
    @JvmField val accessToken: String,
    @JvmField val refreshToken: String
)