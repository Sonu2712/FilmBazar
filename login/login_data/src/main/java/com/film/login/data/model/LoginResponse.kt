package com.film.login.data.model

import com.film.bazar.appusercore.model.User
import com.film.token.manager.data.TokenInfo

data class LoginResponse(
    @JvmField val user: User?,
    @JvmField val tokenInfo: TokenInfo?,
    @JvmField val loginTime: Long,
    @JvmField val forceChange: Boolean = false,
    @JvmField val clientCode: String? = null,
    @JvmField val passwordExpired: Boolean = false
)