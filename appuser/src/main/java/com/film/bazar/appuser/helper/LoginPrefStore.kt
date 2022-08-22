package com.film.bazar.appuser.helper

import com.film.app.core.prefs.IntegerPreference
import com.film.app.core.prefs.LongPreference
import com.film.app.core.prefs.PrefStore
import com.film.app.core.prefs.StringPreference

data class LoginPrefStore(
    @JvmField val userIdentity: StringPreference,
    @JvmField val clientCode: StringPreference,
    @JvmField val multiFactorInfo: StringPreference,
    @JvmField val tLoginInfo: StringPreference,
    @JvmField val multiFactorTime: LongPreference,
    @JvmField val interactiveVersion: IntegerPreference
) : PrefStore {
    override fun clear() {
        userIdentity.delete()
        clientCode.delete()
        multiFactorInfo.delete()
        tLoginInfo.delete()
        multiFactorTime.delete()
        interactiveVersion.delete()
    }
}
