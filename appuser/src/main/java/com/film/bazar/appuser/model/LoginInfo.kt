package com.film.bazar.appuser.model

import com.google.gson.annotations.SerializedName
import com.film.bazar.appusercore.model.UserType

data class LoginInfo @JvmOverloads constructor(
    @SerializedName("userCode")
    @JvmField val userCode: String,
    @SerializedName("password")
    @JvmField val password: String,
    @SerializedName("userType")
    @JvmField val userType: UserType,
    @SerializedName("mobileNo")
    @JvmField val mobileNo: String = ""
)
