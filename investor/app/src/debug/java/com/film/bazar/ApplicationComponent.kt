package com.film.bazar

import com.film.debugview.DebugComponent
import com.film.bazar.data.drawermenu.MenuDataSourceRepositoryImpl
import com.film.bazar.drawermenu.DrawerMenuView
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, DebugModule::class,
        DebugActivityBuilder::class]
)
interface ApplicationComponent : AndroidInjector<MOSLApplicationImpl>, DebugComponent {

    fun inject(target: DrawerMenuView)
    fun inject(target: MenuDataSourceRepositoryImpl)

    override fun inject(instance: MOSLApplicationImpl)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MOSLApplication): Builder

        fun build(): ApplicationComponent
    }
}