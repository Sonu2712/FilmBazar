package com.film.bazar.menu

import androidx.appcompat.app.AppCompatActivity
import com.film.bazar.coreui.navigator.ScreenNavigator
import javax.inject.Inject

interface InterModuleNavigator {
    fun openPortfolio()
}

class InterModuleNavigatorImpl @Inject constructor(
    private val activity: AppCompatActivity,
    protected val screenNavigator: ScreenNavigator
) : InterModuleNavigator {
    override fun openPortfolio() {
    }
}