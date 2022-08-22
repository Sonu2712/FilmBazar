package com.film.bazar.appuser.helper

import com.film.bazar.appusercore.model.UserType

abstract class PageAccessHelper {
    fun isAccessible(userType: UserType, pageId: String): Boolean {
        return when (userType) {
            UserType.OPEN_USER -> isAccessibleToOpenUsers(pageId)
            UserType.GUEST -> isAccessibleToGuestUsers(pageId)
            UserType.MUTUAL_FUND -> isAccessibleToMutualFundUsers(pageId)
            UserType.NON_TRADING -> isAccessibleToNonTradingUsers(pageId)
            UserType.TRADING -> isAccessibleToTradingUsers(pageId)
        }
    }

    protected abstract fun isAccessibleToTradingUsers(pageId: String): Boolean
    protected abstract fun isAccessibleToNonTradingUsers(pageId: String): Boolean
    protected abstract fun isAccessibleToMutualFundUsers(pageId: String): Boolean
    protected abstract fun isAccessibleToGuestUsers(pageId: String): Boolean
    protected abstract fun isAccessibleToOpenUsers(pageId: String): Boolean
}
