package com.film.bazar.profile.helpsupport.writeus

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.commons.data.UiModel
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface WriteToUsView : BaseView {
    fun onSubmitClicked() : Observable<Unit>
    fun getMessage() : String
    fun renderSubmitQuery(uiModel: UiModel<String>)
}

class WriteToUsPresenter @Inject constructor(
    view: WriteToUsView,
    val repository: ProfileRepository
) : BasePresenter<WriteToUsView>(view){
    override fun start() {
        view.onSubmitClicked()
            .map { view.getMessage() }
            .switchMap {
                repository.writeToUs(it)
                    .compose(applyUiModel())
            }.subscribe {
                view.renderSubmitQuery(it)
            }
    }
}

@Module
abstract class WriteToUsModule{
    @Binds
    abstract fun provideWriteToUsView(fragment: WriteUsFragment) : WriteToUsView
}