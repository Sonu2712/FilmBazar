package com.film.bazar.home_ui

import com.film.app.core.base.BasePresenter
import com.film.commons.rx.addTo
import com.film.bazar.home_domain.HomeRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class HomePresenter @Inject constructor(
    view: HomeView,
    val repository: HomeRepository,
    val navHandler: HomeNavigationHandler
) : BasePresenter<HomeView>(view = view) {

    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getHomeData()
                    .compose(applyUiModel())
            }.subscribe(view::renderWelcome)
            .addTo(disposable)

        view.onFilterClicked()
            .subscribe { view.showSortFilterBottomSheet() }
            .addTo(disposable)

        view.onNavigationEvent()
            .subscribe { navHandler.handle(it.event) }
            .addTo(disposable)
    }

}
