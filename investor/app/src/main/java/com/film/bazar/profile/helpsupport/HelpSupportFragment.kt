package com.film.bazar.profile.helpsupport

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentHelpSupportBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class HelpSupportFragment : MOSLCommonFragment(), HelpSupportView {
    lateinit var binding : FragmentHelpSupportBinding
    @Inject
    lateinit var presenter: HelpSupportPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_help_support
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpSupportBinding.bind(view)
        setTitle("Help and Support")
        presenter.start()
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }

    override fun onPaymentRefundClicked(): Observable<Unit> {
        return binding.tvPaymentRefund.clicks()
    }

    override fun onAccountRelatedClicked(): Observable<Unit> {
        return  binding.tvAccountRelated.clicks()
    }

    override fun onWalletClicked(): Observable<Unit> {
        return binding.tvWallet.clicks()
    }

    override fun onInvestmentClicked(): Observable<Unit> {
        return binding.tvInvestment.clicks()
    }

    override fun onWritUsClicked(): Observable<Unit> {
        return binding.tvWriteUs.clicks()
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }
}