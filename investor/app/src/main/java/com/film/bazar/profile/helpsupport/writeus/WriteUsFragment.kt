package com.film.bazar.profile.helpsupport.writeus

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentWriteToUsBinding
import com.film.bazar.profile.helpsupport.writeus.confirmation.ConfirmationBottomSheetFragment
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class WriteUsFragment : MOSLCommonFragment(), WriteToUsView {
    lateinit var binding : FragmentWriteToUsBinding

    @Inject
    lateinit var presenter: WriteToUsPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_write_to_us
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteToUsBinding.bind(view)
        setTitle("Tell us about the issue")
        presenter.start()
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }

    override fun renderSubmitQuery(uiModel: UiModel<String>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }
        uiModel.onSuccess {
            ConfirmationBottomSheetFragment()
                .show(childFragmentManager, it)
        }
    }

    override fun onSubmitClicked(): Observable<Unit> {
        return binding.btnSubmit.clicks()
    }

    override fun getMessage(): String {
        return binding.tvMessgae.text.toString()
    }
}