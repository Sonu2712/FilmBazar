package com.mosl.mobile

import com.mosl.mobile.helper.CrashlyticsTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import coil.Coil
import timber.log.Timber
import coil.ImageLoader


class MOSLApplicationImpl : MOSLApplication() {
  override fun init() {
    Timber.plant(CrashlyticsTree())

    RxJavaPlugins.setErrorHandler { throwable -> Timber.tag("RxError").e(throwable) }

    Coil.setDefaultImageLoader {
      ImageLoader(this) {
        crossfade(true)
        allowHardware(false)
      }
    }
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    val appComponent = DaggerApplicationComponent.builder().application(this).build()
    this.appComponent = appComponent
    return appComponent
  }

  override fun customHTMLCallback(payload: HashMap<String, Any>?) {

  }
}
