package com.mosl.mobile

import com.mosl.mobile.drawermenu.DrawerMenuView
import com.mosl.mobile.settings.aboutapp.AppInfo
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, AndroidSupportInjectionModule::class,
        ActivityBuilder::class, ServiceProvider::class]
)
interface ApplicationComponent : AndroidInjector<MOSLApplicationImpl> {

    abstract fun inject(target: DrawerMenuView)
    abstract fun inject(target: AppInfo)

    override fun inject(instance: MOSLApplicationImpl)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MOSLApplication): Builder

        fun build(): ApplicationComponent
    }
}
