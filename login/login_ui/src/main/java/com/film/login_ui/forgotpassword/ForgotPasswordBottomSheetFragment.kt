package com.film.login_ui.forgotpassword

import android.graphics.Typeface
import android.text.InputFilter
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.coreui.views.AutoReadOTPEvents
import com.film.bazar.coreui.views.ResendData
import com.film.commons.rx.ofType
import com.film.login_ui.R
import com.film.login_ui.databinding.FragmentForgotBottomsheetBinding
import com.film.login_ui.helper.emojiFilter
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ForgotPasswordBottomSheetFragment : DaggerBaseBottomSheetFragment(), ForgotPasswordView {
    lateinit var binding: FragmentForgotBottomsheetBinding

    @Inject
    lateinit var presenter: ForgotPasswordPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_forgot_bottomsheet
    }

    override fun setupView(view: View) {
        binding = FragmentForgotBottomsheetBinding.bind(view)
        presenter.start()
        binding.apply {
            btnDone.isEnabled = false
            btnVerfiy.isEnabled = false
            btnResetDone.isEnabled = false
        }
    }

    override fun setupView(isFromForgotPassword: Boolean) {
        binding.toggleView(isFromForgotPassword = isFromForgotPassword)
    }

    override fun renderDone(isVerifyOtp: Boolean) {
        binding.toggleView(isFromVerifyOtp = isVerifyOtp)
        binding.autoReadOtp.renderSubmit(30)
    }

    override fun renderVerify(isFromReset: Boolean) {
        binding.toggleView(isFromReset = isFromReset)
    }

    fun FragmentForgotBottomsheetBinding.toggleView(
        isFromForgotPassword: Boolean = false,
        isFromVerifyOtp: Boolean = false,
        isFromReset: Boolean = false
    ) {
        val isForgot = isFromForgotPassword && !isFromVerifyOtp && !isFromReset
        val isOtp = isFromVerifyOtp && !isFromForgotPassword && !isFromReset
        val isReset = isFromReset && !isFromForgotPassword && !isFromVerifyOtp
        tvForgotPassword.isVisible = isForgot || isOtp
        tvRegisteredEmailId.isVisible = isForgot
        tilClientCode.isVisible = isForgot
        viewEmail.isVisible = isForgot
        autoReadOtp.isVisible = isForgot
        btnDone.isVisible = isForgot
        btnDone.isVisible = isForgot
        autoReadOtp.isVisible = isOtp
        btnVerfiy.isVisible = isOtp

        tvResetPassword.isVisible = isReset
        tvResetMsg.isVisible = isReset
        tilPassword.isVisible = isReset
        tilConfirmPassword.isVisible = isReset
        btnResetDone.isVisible = isReset
        if (isReset){
            binding.edClientPass.apply {
                typeface = Typeface.DEFAULT
                filters = arrayOf(emojiFilter, InputFilter.LengthFilter(10))
                transformationMethod = PasswordTransformationMethod()
            }
            binding.edClientConfirmPass.apply {
                typeface = Typeface.DEFAULT
                filters = arrayOf(emojiFilter, InputFilter.LengthFilter(10))
                transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    override fun onCloseClicked(): Observable<Unit> {
        return binding.imgClose.clicks()
    }

    override fun getEmailId(): String {
        return binding.tvRegisteredEmailId.text.toString()
    }

    override fun renderResendOtp() {
        binding.autoReadOtp.renderResend(
            ResendData(
                resendCounter = 1,
                retryAfter = 30
            ), false
        )
    }

    override fun onResendOtpClicked(): Observable<AutoReadOTPEvents.ResendOTPEvent> {
        return binding.autoReadOtp.autoReadOTPEvents.ofType()
    }

    override fun onDoneClicked(): Observable<Unit> {
        return binding.btnDone.clicks()
    }

    override fun onVerifyClicked(): Observable<Unit> {
        return binding.btnVerfiy.clicks()
    }

    override fun onResetDoneClicked(): Observable<Unit> {
        return binding.btnResetDone.clicks()
    }

    override fun renderResetDone() {
        dismiss()
        Toast.makeText(requireContext(), "Your password has been changed successfully", Toast.LENGTH_LONG).show()
    }

    override fun onEmailIdChanged(): Observable<Int> {
        return binding.edClientCode.textChanges().map { it.length }
    }

    override fun toggleDoneButton(isEnabled: Boolean) {
        binding.btnDone.isEnabled = isEnabled
    }

    override fun onOtpChanged(): Observable<AutoReadOTPEvents.ToggleSubmitButtonEvent> {
        return binding.autoReadOtp.autoReadOTPEvents.ofType()
    }

    override fun toggleVerifyButton(isEnabled: Boolean) {
        binding.btnVerfiy.isEnabled = isEnabled
    }

    override fun onPasswordChanged(): Observable<String> {
        return binding.edClientPass.textChanges().map { it.toString() }
    }

    override fun onConfirmPasswordChanged(): Observable<String> {
        return binding.edClientConfirmPass.textChanges().map { it.toString() }
    }

    override fun toggleRestButton(isEnabled: Boolean) {
        binding.btnResetDone.isEnabled = isEnabled
    }


    override fun dismissBottomSheet() {
        dismiss()
    }
}