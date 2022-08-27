package com.film.bazar.profile.paymentdetails

import android.os.Bundle
import android.view.View
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentUserPaymentDetailBinding
import com.film.bazar.databinding.FragmentWelcomeBinding
import com.film.bazar.domain.drawermenu.profile.UserPaymentDetail
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class PaymentDetailsFragment : MOSLCommonFragment(), PaymentDetailView {
    lateinit var binding: FragmentUserPaymentDetailBinding

    @Inject
    lateinit var presenter: PaymentDetailPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_user_payment_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserPaymentDetailBinding.bind(view)
        setTitle("Payment details")
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    override fun renderPaymentDetail(uiModel: UiModel<UserPaymentDetail>) {
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

    override fun getPaymentDetail(): UserPaymentDetail {
        return UserPaymentDetail(
            bankName = binding.edtBankName.text.toString(),
            accHolderName = binding.edtAccHolderName.text.toString(),
            accNumber = binding.edtAccountNumber.text.toString(),
            ifscCode = binding.edtIfscCode.text.toString()
        )
    }

    fun FragmentUserPaymentDetailBinding.bindData(data: UserPaymentDetail) {
        edtBankName.setText(data.bankName)
        edtAccHolderName.setText(data.accHolderName)
        edtAccountNumber.setText(data.accNumber)
        edtIfscCode.setText(data.ifscCode)
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }
}