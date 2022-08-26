package com.film.bazar.home_ui.detail

import com.film.app.core.base.BasePresenter
import com.film.bazar.home_domain.HomeRepository
import com.film.bazar.home_ui.HomeNavigationHandler
import com.film.commons.rx.addTo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MovieDetailPresenter @Inject constructor(
    view: MovieDetailView,
    val repository: HomeRepository,
    val navHandler: HomeNavigationHandler
) : BasePresenter<MovieDetailView>(view = view){

    override fun start() {
        Observable.just(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getMovieDetail()
                    .compose(applyUiModel())
            }.subscribe(view::render)
            .addTo(disposable)

        view.openCastCrew()
            .switchMap {
                repository.getCastCrew(it.id)
                    .compose(applyUiModel())
            }.subscribe(view::renderCarsCrew)
            .addTo(disposable)

        view.onNavigationEvent()
            .subscribe { navHandler.handle(it.event) }
            .addTo(disposable)
    }
}