package com.film.login_ui.openanaccount

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import com.film.login_ui.R
import com.film.login_ui.databinding.FragmentOpenAnAccountBinding
import com.film.login_ui.helper.emojiFilter
import com.film.login_ui.verification.VerificationBottomSheetFragment
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class OpenAnAccountFragment : BaseFragment(), OpenAnAccountView, TextWatcher {
    lateinit var binding: FragmentOpenAnAccountBinding

    @Inject
    lateinit var presenter: OpenAnAccountPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_open_an_account
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOpenAnAccountBinding.bind(view)
        presenter.start()
        binding.edPassword.apply {
            typeface = Typeface.DEFAULT
            filters = arrayOf(emojiFilter, InputFilter.LengthFilter(10))
            transformationMethod = PasswordTransformationMethod()
        }
        binding.edPassword.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        updatePasswordStrengthView(s.toString())
    }

    /* override fun togglePasswordStrength(password: String) {
         updatePasswordStrengthView(password)
     }*/

    override fun onNameChanged(): Observable<String> {
        return binding.edFullName.textChanges().map { it.toString() }
    }

    override fun onEmailChanged(): Observable<String> {
        return binding.edEmailId.textChanges().map { it.toString() }
    }

    override fun onNumberChangedChanged(): Observable<String> {
        return binding.edMobileNumber.textChanges().map { it.toString() }
    }

    override fun toggleRegisterButton(isEnabled: Boolean) {
        binding.btnDone.isEnabled = isEnabled
    }

    override fun onPasswordChanged(): Observable<String> {
        return binding.edPassword.textChanges().map { it.toString() }
    }

    override fun onRegisterClicked(): Observable<Unit> {
        return binding.btnDone.clicks()
    }

    override fun showVerifyBottomSheet() {
        VerificationBottomSheetFragment().apply {

        }.show(childFragmentManager, "VerificationBottomSheetFragment")
    }

    private fun updatePasswordStrengthView(password: String) {
        val context = requireContext()
        val progressBar = binding.progressBar
        val strengthView = binding.passwordStrength
        if (TextView.VISIBLE != strengthView.visibility)
            return

        if (TextUtils.isEmpty(password)) {
            strengthView.text = ""
            progressBar.progress = 0
            return
        }

        val str = PasswordStrength.calculateStrength(password)
        strengthView.text = str.getText(context)
        strengthView.setTextColor(ContextCompat.getColor(context, str.color))

        progressBar.progressDrawable.setColorFilter(
            ContextCompat.getColor(context, str.color),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        if (str.getText(context) == "Weak") {
            progressBar.progress = 25
        } else if (str.getText(context) == "Medium") {
            progressBar.progress = 50
        } else if (str.getText(context) == "Good") {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }
}