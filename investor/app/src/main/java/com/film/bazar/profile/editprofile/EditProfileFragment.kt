package com.film.bazar.profile.editprofile

import android.os.Bundle
import android.view.View
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentEditProfileBinding
import com.film.bazar.databinding.FragmentWelcomeBinding
import com.film.bazar.domain.drawermenu.profile.UserProfile
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class EditProfileFragment : MOSLCommonFragment(), EditProfileView {
    lateinit var binding: FragmentEditProfileBinding

    @Inject
    lateinit var presenter: EditProfilePresenter

    override fun getLayout(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)
        setTitle("Edit Profile")
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    override fun renderUserProfileInfo(uiModel: UiModel<UserProfile>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }

        uiModel.onSuccess {
            binding.bindData(it)
        }
    }

    override fun renderSave(uiModel: UiModel<String>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }

        uiModel.onSuccess {
            toast(it)
        }
    }

    override fun onSaveClicked(): Observable<Unit> {
        return binding.btnSave.clicks()
    }

    override fun getUserProfile(): UserProfile {
        return UserProfile(
            mNumber = binding.edtMobile.text.toString(),
            emailId = binding.edtEmailId.text.toString(),
            panNumber = binding.edtPanNumber.text.toString(),
            address = binding.edtAddress.text.toString()
        )
    }

    fun FragmentEditProfileBinding.bindData(userProfile: UserProfile) {
        edtMobile.setText(userProfile.mNumber)
        edtEmailId.setText(userProfile.mNumber)
        edtPanNumber.setText(userProfile.mNumber)
        edtAddress.setText(userProfile.mNumber)
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }
}