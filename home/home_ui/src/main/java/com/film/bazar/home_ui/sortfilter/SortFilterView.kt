package com.film.bazar.home_ui.sortfilter

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.home_domain.HomeRepository
import com.film.bazar.home_domain.MovieFilter
import com.film.bazar.home_domain.MovieSort
import com.film.bazar.home_domain.MovieSortKeyValue
import com.film.bazar.home_ui.HomeUiEvent
import com.film.commons.data.UiModel
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface SortFilterView : BaseView {
    fun render(uiModel: UiModel<MovieSort>)
    fun onApplyClicked() : Observable<Unit>
    fun onItemClicked(): Observable<HomeUiEvent.SortItemClicked>
    fun overrideSortFilter(sortKeyValue: MovieSortKeyValue)
    fun getFilter() : MovieSort
    fun dismissFilter(uiModel: UiModel<MovieSort>)
}

class SortFilterPresenter @Inject constructor(
    view : SortFilterView,
    val homeRepository: HomeRepository
) : BasePresenter<SortFilterView>(view){

    override fun start() {
        onFetchCalled()
            .switchMap {
                homeRepository.getMovieSort()
                    .compose(applyUiModel())
            }.subscribe(view::render)
            .addTo(disposable)

        view.onItemClicked()
            .subscribe {
                view.overrideSortFilter(it.filter)
            }.addTo(disposable)

        view.onApplyClicked()
            .map { view.getFilter() }
            .switchMap {
                homeRepository.saveFilter(it)
                    .compose(applyUiModel())
            }.subscribe {
                view.dismissFilter(it)
            }.addTo(disposable)
    }
}

@Module
abstract class SortFilterModule{
    @Binds
    abstract fun provideSortFilterView(fragment: SortFilterBottomSheetFragment) : SortFilterView
}