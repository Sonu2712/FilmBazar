package com.film.login.data.model

import com.film.bazar.appusercore.model.UserType


data class LoginFormOutput @JvmOverloads constructor(
    @JvmField val userCode: String,
    @JvmField val clientCode: String = "",
    @JvmField val password: String,
    @JvmField val userType: UserType,
    @JvmField val mobileNo: String = ""
)
