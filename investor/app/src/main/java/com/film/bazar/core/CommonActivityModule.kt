package com.film.bazar.core

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.film.bazar.coreui.appcoreui.base.ActivityDelegate
import com.film.bazar.FragmentProvider
import com.film.bazar.MOSLApplication
import com.film.bazar.bottombar.BottomBarHelper
import com.film.bazar.bottombar.BottomBarView
import com.film.bazar.coreui.core.ContainerManager
import com.film.bazar.menu.NavigatorAliasProvider
import com.film.bazar.util.NavigationCallbackImpl
import com.film.bazar.coreui.navigatorlib.FragmentTransitionHelper
import com.film.bazar.coreui.navigatorlib.FragmentTransitionHelperImpl
import com.film.bazar.coreui.navigatorlib.NavigationCallback
import com.film.bazar.coreui.navigatorlib.Navigator
import dagger.Binds
import dagger.Module

@Module(includes = [FragmentProvider::class, NavigatorAliasProvider::class])
abstract class CommonActivityModule {

    @Binds
    internal abstract fun provideApplication(application: MOSLApplication): Application

    @Binds
    internal abstract fun provideNavigationCallback(
        navigationCallback: NavigationCallbackImpl
    ): NavigationCallback

    @Binds
    internal abstract fun provideFragmentTransitionHelper(
        transitionHelper: FragmentTransitionHelperImpl
    ): FragmentTransitionHelper

    @Binds
    internal abstract fun provideFragmentActivity(activity: MOSLCommonActivity): FragmentActivity

    @Binds
    internal abstract fun provideAppCompatActivity(activity: MOSLCommonActivity): AppCompatActivity

    @Binds
    internal abstract fun provideNavigator(activity: MOSLCommonActivity): Navigator

    @Binds
    internal abstract fun provideContainerManager(activity: MOSLCommonActivity): ContainerManager

    @Binds
    internal abstract fun provideActivityDelegate(activity: MOSLCommonActivity): ActivityDelegate

    @Binds
    internal abstract fun provideBottomBarHelper(bottomBarView: BottomBarView): BottomBarHelper
}
