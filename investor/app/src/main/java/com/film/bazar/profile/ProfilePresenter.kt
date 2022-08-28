package com.film.bazar.profile

import com.film.app.core.base.BasePresenter
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.navigator.ScreenNavigator
import com.film.bazar.domain.drawermenu.profile.ProfileRepository
import com.film.commons.data.onSuccess
import com.film.commons.rx.addTo
import com.film.login.data.repository.LoginRepository
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    view: ProfileView,
    var loginRepository: LoginRepository,
    val screenNavigator: ScreenNavigator,
    val profileRepository: ProfileRepository
) : BasePresenter<ProfileView>(view) {

    override fun start() {
        view.onPaymentDetailsClicked()
            .subscribe {
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_PAYMENT_DETAILS_FRAGMENT,
                    true
                )
            }.addTo(disposable)

        view.onNotificationClicked()
            .skip(1)
            .switchMap {
                profileRepository.saveNotificationStatus(it)
                    .compose(applyUiModel())
            }.subscribe(view::renderNotification)
            .addTo(disposable)

        view.onLocationPermissionClicked()
            .subscribe {
                view.showLocationPermissionPopup()
            }

        view.onLogoutClicked()
            .subscribe { view.showLogoutConfirmationDialog() }
            .addTo(disposable)

        view.onEditProfileClicked()
            .subscribe {
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_EDIT_PROFILE_FRAGMENT,
                    true
                )
            }
            .addTo(disposable)

        view.onHelpSupportClicked()
            .subscribe {
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_HELP_SUPPORT_FRAGMENT,
                    true
                )
            }.addTo(disposable)

        view.onTermsConditionClicked()
            .subscribe {
                screenNavigator.openPage(
                    NavigationConstants.NAVIGATE_TO_TERMS_CONDITION_FRAGMENT,
                    true
                )
            }.addTo(disposable)


        view.onLogoutConfirmed()
            .switchMap {
                loginRepository
                    .logoutUser()
                    .toSingleDefault(true)
                    .toObservable()
                    .compose(applyUiModel())
            }.subscribe {
                view.renderSuccessLogout(it)
            }
            .addTo(disposable)
    }
}