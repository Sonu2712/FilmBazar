package com.film.bazar.profile.helpsupport.writeus.confirmation

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface ConfirmationBottomSheetView : BaseView {
    fun onSubmitClicked(): Observable<Unit>
    fun onCloseClicked(): Observable<Unit>
    fun dismissBottomSheet()
}

class ConfirmationBottomSheetPresenter @Inject constructor(
    view: ConfirmationBottomSheetView,
    val repository: ProfileRepository,
    val screenNavigator: ScreenNavigator
) : BasePresenter<ConfirmationBottomSheetView>(view) {

    override fun start() {
        view.onSubmitClicked()
            .subscribe {
                screenNavigator.openPage(NavigationConstants.NAVIGATE_TO_HOME)
            }.addTo(disposable)

        view.onCloseClicked()
            .subscribe {
                view.dismissBottomSheet()
            }.addTo(disposable)
    }
}

@Module
abstract class ConfirmationBottomSheetModule {
    @Binds
    abstract fun provideConfirmationBottomSheetView(fragment: ConfirmationBottomSheetFragment): ConfirmationBottomSheetView
}