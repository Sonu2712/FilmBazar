package com.film.bazar.profile.editprofile

import com.film.app.core.base.BasePresenter
import com.film.app.core.base.BaseView
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.bazar.domain.drawermenu.profile.UserProfile
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.commons.rx.addTo
import dagger.Binds
import dagger.Module
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

interface EditProfileView : BaseView {
    fun renderUserProfileInfo(uiModel: UiModel<UserProfile>)
    fun getUserProfile(): UserProfile
    fun onSaveClicked(): Observable<Unit>
    fun renderSave(uiModel: UiModel<String>)
}

class EditProfilePresenter @Inject constructor(
    view: EditProfileView,
    val repository: ProfileRepository,
    val screenNavigator: ScreenNavigator
) : BasePresenter<EditProfileView>(view) {

    override fun start() {
        Observable.merge(onFetchCalled(), onRetryCalled())
            .switchMap {
                repository.getUserProfile()
                    .compose(applyUiModel())
            }.subscribe(view::renderUserProfileInfo)
            .addTo(disposable)

        view.onSaveClicked()
            .map { view.getUserProfile() }
            .switchMap {
                repository.saveUserProfile(it)
                    .compose(applyUiModel())
            }.subscribe {
                view.renderSave(it)
                it.onSuccess {
                    screenNavigator.goBack()
                }
            }
            .addTo(disposable)
    }
}

@Module
abstract class EditProfileModule {
    @Binds
    abstract fun provideEditProfileView(fragment: EditProfileFragment): EditProfileView
}