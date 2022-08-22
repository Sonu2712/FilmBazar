package com.film.bazar.appusercore.model

import com.google.gson.annotations.SerializedName

enum class UserType(@JvmField val label: String) {
    /**
     * This state represents the application before any user has logged in.
     */
    @SerializedName("OU")
    OPEN_USER("OU"),
    @SerializedName("T")
    TRADING("T"),
    @SerializedName("N")
    NON_TRADING("N"),
    @SerializedName("M")
    MUTUAL_FUND("M"),
    @SerializedName("G")
    GUEST("G");

    val userTypeString: String
        get() {
            return if (isCustomer) {
                "Customer"
            } else {
                "Guest"
            }
        }

    val mfUserString: String
        get() {
            if (isMFICustomer) {
                return "MFI"
            } else if (isMFDCustomer) {
                return "MFD"
            }
            return ""
        }

    val isMFICustomer: Boolean
        get() = this == TRADING || this == NON_TRADING

    val isMFDCustomer: Boolean
        get() = this == MUTUAL_FUND

    val isCustomer: Boolean
        get() = isMFICustomer || isMFDCustomer

    val isGuest: Boolean
        get() = this == GUEST

    override fun toString(): String {
        return label
    }

    companion object {
        @JvmStatic
        fun from(type: String): UserType {
            for (value in values()) {
                if (value.label == type) {
                    return value
                }
            }
            return OPEN_USER
        }
    }
}
