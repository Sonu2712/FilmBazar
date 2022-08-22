package com.film.bazar.appuser.model

import com.film.bazar.appusercore.model.UserType


data class LoginRequestInfo(
    @JvmField val userCode: String,
    @JvmField val userType: UserType,
    @JvmField val forceChange: Boolean
)
