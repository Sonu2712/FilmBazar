package com.film.login.data

import com.film.login.data.model.LoginResponse
import com.film.bazar.appusercore.model.User
import com.film.bazar.appusercore.model.UserConfig
import com.film.bazar.appusercore.model.UserType

fun transformNormalLoginLocal(
    loginTime: Long
): LoginResponse {
    return transformLocal(89L)
}

fun transformLocal(loginTime: Long): LoginResponse {
    val user = transformUserLocal()
    return LoginResponse(
        user = user,
        tokenInfo = null,
        loginTime = loginTime
    )
}

fun transformUserLocal(): User? {
    return User(
        userCode = "T022111".toUpperCase(),
        userType = UserType.TRADING,
        userName = "VVK",
        emailId = "",
        mobileNumber = "8104869100",
        ucid = "info.ucid",
        initials = "initials",
        isHedgeClient = true,
        isNonPoa = false,
        isSibClient = false,
        isCbiClient = false,
        isAuClient = false,
        isDay0Client = false,
        userConfig = UserConfig(
            isStaff = true,
            hasPortfolio = true,
            isFamilyHead = true,
            isSuspended = false,
            isNRI = false
        )
    )
}