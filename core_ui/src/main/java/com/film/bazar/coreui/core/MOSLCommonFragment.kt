package com.film.bazar.coreui.core

import androidx.annotation.CallSuper
import com.film.bazar.coreui.navigator.ScreenNavigator
import javax.inject.Inject

open class MOSLCommonFragment : InvestorBaseFragment() {
    @Inject
    lateinit var screenNavigator: ScreenNavigator

    @CallSuper
    override fun onFragmentResume() {
        super.onFragmentResume()
//        Crashlytics.setString("VisiblePage", this.toString())
    }
}
