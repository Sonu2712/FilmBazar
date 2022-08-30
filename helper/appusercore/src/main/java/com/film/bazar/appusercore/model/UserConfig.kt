package com.film.bazar.appusercore.model

import com.google.gson.annotations.SerializedName

data class UserConfig(
    @SerializedName("isStaff")
    @JvmField val isStaff: Boolean,
    @SerializedName("hasPortfolio")
    @JvmField val hasPortfolio: Boolean,
    @SerializedName("isFamilyHead")
    @JvmField val isFamilyHead: Boolean,
    @SerializedName("isSuspended")
    @JvmField val isSuspended: Boolean,
    @SerializedName("dormantFrequency")
    @JvmField val suspendedFrequency: Int = 0,
    @SerializedName("hasBanner")
    @JvmField val hasBanner: Boolean = false,
    @SerializedName("userBanner")
    @JvmField val userBanner: BannerData? = null,
    @SerializedName("isNRI")
    @JvmField val isNRI: Boolean,
    @SerializedName("userSettings")
    @JvmField val userSettings: UserSettings? = UserSettings(
        isWhatsappConsent = true,
        isDpAlert = false,
        onPageHelp = false,
        startupScreen = "home",
        language = AppLanguage.ENGLISH,
        bottomMenu = listOf("home", "portfolio", "wallet", "profile"),
        adBanner = true
    ),
    @SerializedName("portfolioVideoUrl")
    @JvmField val portfolioVideoUrl: String = ""
)

data class BannerData(
    @SerializedName("bannerId")
    @JvmField val bannerId: String,
    @SerializedName("imageUrl")
    @JvmField val imageUrl: String,
    @SerializedName("deepLinkUrl")
    @JvmField val deepLinkUrl: String,
    @SerializedName("displayFrequency")
    @JvmField val frequency: Int
)

data class UserSettings(
    @SerializedName("isWhatappConsent")
    @JvmField val isWhatsappConsent: Boolean,
    @SerializedName("isDpAlert")
    @JvmField val isDpAlert: Boolean,
    @SerializedName("onPageHelp")
    @JvmField val onPageHelp: Boolean,
    @SerializedName("startupScreen")
    @JvmField val startupScreen: String,
    @SerializedName("language")
    @JvmField val language: AppLanguage = AppLanguage.ENGLISH,
    @SerializedName("bottomMenu")
    @JvmField var bottomMenu: List<String>,
    @SerializedName("adBanner")
    @JvmField val adBanner: Boolean = false,
    @SerializedName("optionBrainConsent")
    @JvmField val obConsent: Boolean = false
)