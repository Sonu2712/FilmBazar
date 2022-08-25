package com.film.bazar.menu

import com.film.bazar.coreui.navigator.CommonNavigator
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.coreui.navigator.UrlNavigator
import com.film.bazar.helper.CustomNavigator
import com.film.bazar.helper.CustomNavigatorImpl
import com.film.bazar.helper.urlnavigator.UrlNavigatorImpl
import com.film.bazar.coreui.navigatorlib.BaseNavigator
import com.film.bazar.home_ui.HomeInterNavigator
import dagger.Binds
import dagger.Module

@Module
abstract class NavigatorAliasProvider {
    @Binds
    abstract fun provideCustomNavigator(urlNavigator: CustomNavigatorImpl): CustomNavigator

    @Binds
    abstract fun provideScreenNavigator(screenNavigator: ScreenNavigatorImpl): ScreenNavigator

    @Binds
    abstract fun provideBaseNavigator(screenNavigator: ScreenNavigatorImpl): BaseNavigator

    @Binds
    abstract fun provideInterModuleNavigator(interScreenNavigatorImpl: InterModuleNavigatorImpl): InterModuleNavigator

    @Binds
    abstract fun provideUrlNavigator(urlNavigator: UrlNavigatorImpl): UrlNavigator

    @Binds
    abstract fun provideCommonNavigator(screenNavigator: ScreenNavigatorImpl): CommonNavigator

    @Binds
    abstract fun provideHomeInterNavigator(interNavigatorImpl: InterModuleNavigatorImpl): HomeInterNavigator

}
