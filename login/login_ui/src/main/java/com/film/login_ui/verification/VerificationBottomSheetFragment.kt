package com.film.login_ui.verification

import android.view.View
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.coreui.views.AutoReadOTPEvents
import com.film.bazar.coreui.views.ResendData
import com.film.commons.rx.ofType
import com.film.login_ui.R
import com.film.login_ui.databinding.FragmentVerificationBottomSheetBinding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class VerificationBottomSheetFragment : DaggerBaseBottomSheetFragment(),
    VerificationBottomSheetView {
    lateinit var binding: FragmentVerificationBottomSheetBinding

    @Inject
    lateinit var presenter: VerificationBottomSheetPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_verification_bottom_sheet
    }

    override fun setupView(view: View) {
        binding = FragmentVerificationBottomSheetBinding.bind(view)
        presenter.start()
        binding.autoReadOtp.renderSubmit(30)
    }

    override fun onVerifyClicked(): Observable<Unit> {
        return binding.btnVerify.clicks()
    }

    override fun onOtpChanged(): Observable<AutoReadOTPEvents.ToggleSubmitButtonEvent> {
        return binding.autoReadOtp.autoReadOTPEvents.ofType()
    }

    override fun toggleVerifyButton(isEnabled: Boolean) {
        binding.btnVerify.isEnabled = isEnabled
    }

    override fun onCloseClicked(): Observable<Unit> {
        return binding.imgClose.clicks()
    }

    override fun dismissBottomSheet() {
        dismiss()
    }

    override fun renderVerify(isFromReset: Boolean) {

    }

    override fun onResendOtpClicked(): Observable<AutoReadOTPEvents.ResendOTPEvent> {
        return binding.autoReadOtp.autoReadOTPEvents.ofType()
    }

    override fun getEmailId(): String {
        return ""
    }

    override fun renderResendOtp() {
        binding.autoReadOtp.renderResend(
            ResendData(
                resendCounter = 1,
                retryAfter = 30
            ), false
        )
    }
}