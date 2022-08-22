package com.film.bazar.dashboard

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@Module
abstract class DashboardModule {
  @Binds
  abstract fun provideDashboardView(fragment: DashboardFragment): DashboardContract.View
}

interface DashboardContract {
  interface View : BaseView {
    fun onSearchChanged(): Observable<CharSequence>
    fun renderPage(data: List<DashboardHeader>)
  }
}

class DashboardPresenter @Inject constructor(
    view: DashboardContract.View
) : BasePresenter<DashboardContract.View>(view) {

  override fun start() {
  }
}
