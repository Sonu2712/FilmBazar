package com.film.bazar.profile

import android.os.Bundle
import android.view.View
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.appcoreui.dialog.showDialogForConfirmation
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentProfileBinding
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class ProfileFragment : MOSLCommonFragment(), ProfileView {
    lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var presenter: ProfilePresenter
    val uiEvent = PublishSubject.create<ProfileUiEvent>()

    override fun getLayout(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        setTitle("Profile")
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.showBackNavigation()
    }

    private fun setupRecyclerView() {
    }

    override fun onLogoutClicked(): Observable<Unit> {
        return binding.button.clicks()
    }

    override fun showLogoutConfirmationDialog() {
        requireActivity()?.showDialogForConfirmation(
            getString(R.string.app_msg_logout_alert),
            negativeButton = getString(R.string.button_label_cancel),
            cancelable = false
        ) { positiveClicked ->
            if (positiveClicked) {
                uiEvent.onNext(ProfileUiEvent.LogoutConfirmed)
            }
        }
    }

    override fun renderNotification(uiModel: UiModel<String>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }
        uiModel.onSuccess { toast(it) }
    }

    override fun onNotificationClicked(): Observable<Boolean> {
        return binding.swNotification.checkedChanges()
    }

    override fun onLogoutConfirmed(): Observable<ProfileUiEvent.LogoutConfirmed> {
        return uiEvent.ofType()
    }

    override fun renderSuccessLogout(uiModel: UiModel<Boolean>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }
    }

    override fun onEditProfileClicked(): Observable<Unit> {
        return binding.tvEditProfile.clicks()
    }

    override fun onHelpSupportClicked(): Observable<Unit> {
        return binding.tvHelpSupport.clicks()
    }

    override fun onTermsConditionClicked(): Observable<Unit> {
        return binding.tvTermsCond.clicks()
    }

    override fun onPaymentDetailsClicked(): Observable<Unit> {
        return binding.lyBank.clicks()
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }
}