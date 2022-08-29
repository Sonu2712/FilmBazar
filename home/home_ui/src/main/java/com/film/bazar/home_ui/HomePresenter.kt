package com.film.bazar.home_ui

import com.film.app.core.base.BasePresenter
import com.film.bazar.home_data.repository.resetSortFilter
import com.film.commons.rx.addTo
import com.film.bazar.home_domain.HomeRepository
import com.film.bazar.home_domain.MovieTab
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber
import javax.inject.Inject

class HomePresenter @Inject constructor(
    view: HomeView,
    val repository: HomeRepository,
    val navHandler: HomeNavigationHandler,
    val navigator: HomeNavigator
) : BasePresenter<HomeView>(view = view) {

    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getHomeData()
                    .compose(applyUiModel())
            }.subscribe(view::renderWelcome)
            .addTo(disposable)

        view.onMovieTabClicked()
            .map { view.getSelectedMovieTab() }
            .doOnNext { repository.saveMovieTab(it) }
            .switchMap {
                repository.getMovieByProject(it)
                    .compose(applyUiModel())
            }.subscribe(view::renderMovie)
            .addTo(disposable)

        view.sortFilterApplied()
            .map { view.getSelectedMovieTab() to it.filter }
            .doOnNext { repository.saveMovieTab(it.first) }
            .switchMap {
                repository.getMovieByProject(it.first, it.second)
                    .compose(applyUiModel())
            }
            .subscribe {
                view.renderMovie(it)
            }.addTo(disposable)

        view.onFilterClicked()
            .map { view.getSelectedMovieTab() }
            .subscribe { view.showSortFilterBottomSheet(it ?: MovieTab.ONGOING_PROJECT) }
            .addTo(disposable)

        view.onMovieInfoClicked()
            .map { it.copy(tabType = view.getSelectedMovieTab().toString()) }
            .subscribe {
                navigator.openMovieDetail(it.id, it.tabType)
            }.addTo(disposable)

        view.onNavigationEvent()
            .subscribe { navHandler.handle(it.event) }
            .addTo(disposable)
    }

}
