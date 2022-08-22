package com.film.bazar.appusercore.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userCode")
    @JvmField val userCode: String,
    @SerializedName("userType")
    @JvmField val userType: UserType,
    @SerializedName("userName")
    @JvmField val userName: String?,
    @SerializedName("emailId")
    @JvmField val emailId: String?,
    @SerializedName("isHedgeClient")
    @JvmField val isHedgeClient: Boolean = false,
    @SerializedName("isNONPOA")
    @JvmField val isNonPoa: Boolean,
    @SerializedName("isSibClient")
    @JvmField val isSibClient: Boolean,
    @SerializedName("isCbiClient")
    @JvmField val isCbiClient: Boolean,
    @SerializedName("isDay0Client")
    @JvmField val isDay0Client: Boolean,
    @SerializedName("isAlgo")
    @JvmField val isAlgo: Boolean = false,
    @SerializedName("isAuClient")
    @JvmField val isAuClient: Boolean,
    @SerializedName("mobileNumber")
    @JvmField val mobileNumber: String?,
    @SerializedName("initials")
    @JvmField val initials: String,
    @SerializedName("ucid")
    @JvmField val ucid: String,
    @SerializedName("userConfig")
    @JvmField val userConfig: UserConfig,
    @SerializedName("bestPriceExecutionDelay") @JvmField val bestPriceExecutionDelay: Int = 0
)