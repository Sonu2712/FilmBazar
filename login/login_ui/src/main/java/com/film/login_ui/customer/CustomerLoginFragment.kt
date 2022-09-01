package com.film.login_ui.customer

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.film.login.data.model.LoginResponse
import com.film.login_ui.LoginType
import com.film.login_ui.R
import com.film.login_ui.databinding.FragmentCustomerLoginBinding
import com.film.login_ui.helper.emojiFilter
import com.film.login_ui.navigationEvent
import com.film.login_ui.verifypan.LoginUiEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.editorActions
import com.film.app.core.events.DataAction
import com.film.app.core.prefs.BooleanPreference
import com.film.app.core.prefs.StringPreference
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import com.film.commons.appinfo.AppInfo
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.annotations.AppMenu
import com.film.annotations.FromForgotPassword
import com.film.annotations.GuestSignUp
import com.film.annotations.PreLogin
import com.film.bazar.appusercore.model.UserType
import com.film.login_ui.forgotpassword.ForgotPasswordBottomSheetFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class CustomerLoginFragment : BaseFragment(), LoginView, View.OnClickListener {
    lateinit var binding: FragmentCustomerLoginBinding

    @Inject
    lateinit var presenter: LoginPresenter

    @Inject
    @GuestSignUp
    lateinit var guestSignUp: BooleanPreference

    @Inject
    @AppMenu
    lateinit var appMenuPref: StringPreference

    @Inject
    @FromForgotPassword
    lateinit var fromForgotPassPref: BooleanPreference

    @Inject
    @PreLogin
    lateinit var preLogin: BooleanPreference

    @Inject
    lateinit var appInfo: AppInfo

    @Inject
    lateinit var preferences: SharedPreferences

    private val loginUiEvents = PublishSubject.create<LoginUiEvent>()

    lateinit var selectedView: TextView
    lateinit var colorStateList: ColorStateList

    override fun getLayout(): Int {
        return R.layout.fragment_customer_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCustomerLoginBinding.bind(view)
        setHasOptionsMenu(preLogin.get())
        setUpView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    private fun setUpView() {
        selectedView = binding.toggleButtonGroup.select
        colorStateList = binding.toggleButtonGroup.item2.textColors
        binding.toggleButtonGroup.item1.setOnClickListener(this)
        binding.toggleButtonGroup.item2.setOnClickListener(this)

        binding.tilPassword.apply {
            getString(R.string.hint_password_customer_login)
        }
        binding.edClientPass.apply {
            typeface = Typeface.DEFAULT
            filters = arrayOf(emojiFilter, InputFilter.LengthFilter(10))
            transformationMethod = PasswordTransformationMethod()
        }

        binding.edClientCode.apply {
            filters = arrayOf(emojiFilter, InputFilter.LengthFilter(100))
        }

        val guestView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_guest_info, null)

        val builder = MaterialAlertDialogBuilder(requireContext()).setView(guestView)
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        val btnTryNow = guestView.findViewById<Button>(R.id.btnTryNow)
        val btnClose = guestView.findViewById<ImageView>(R.id.btn_close)

        btnClose.setOnClickListener {
            alertDialog.dismiss()
        }

        btnTryNow.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.item1) {
            binding.toggleButtonGroup.item2.background = null
            selectedView.isVisible = true
            selectedView.animate().x(0f).setDuration(100)
            binding.apply {
                toggleButtonGroup.item1.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.film_blue_light
                    )
                )
                toggleButtonGroup.item2.setTextColor(colorStateList)
                tilClientCode.visibility = View.VISIBLE
                txtForgotPass.visibility = View.VISIBLE
                btnLogin.text = "Login"
            }
            //uiEventSubject.onNext(OrderPreviewEvent.OnMarketSwitchChanged(true))
        } else {
            binding.apply {
                setLimitView(false)
                tilClientCode.visibility = View.VISIBLE
                txtForgotPass.visibility = View.GONE
                btnLogin.text = "Request OTP"
            }
            //uiEventSubject.onNext(OrderPreviewEvent.OnMarketSwitchChanged(false))
        }
    }

    fun FragmentCustomerLoginBinding.setLimitView(isDefaultLimit: Boolean) {
        toggleButtonGroup.item1.setTextColor(colorStateList)
        toggleButtonGroup.item2.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.film_blue_light
            )
        )
        if (isDefaultLimit) {
            toggleButtonGroup.item2.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.back_select)
            toggleButtonGroup.item1.background = null
            selectedView.isVisible = false
        } else {
            val size = binding.toggleButtonGroup.item2.width.toFloat()
            selectedView.animate().x(size).setDuration(100)
        }
    }

    override fun onDestroyView() {
        presenter.stop()
        clearPref()
        super.onDestroyView()
    }

    override fun onDestroy() {
        clearPref()
        super.onDestroy()
    }

    override fun onFragmentPause() {
        clearPref()
        super.onFragmentPause()
    }

    private fun clearPref() {
        fromForgotPassPref.delete()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.customer_login_menu, menu)
        val menuText = menu.findItem(R.id.viewMarkets).actionView as? TextView
        menuText?.apply {
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(requireContext(), R.color.action_text_color))
            setText(R.string.menu_view_markets)
            setPadding(16, 0, 16, 0)
            /* setOnClickListener {
                 uiEvents.onNext("ViewMarkets")
             }*/
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onLoginClicked(): Observable<Unit> {
        return binding.btnLogin.clicks()
    }

    override fun onDoneClicked(): Observable<Int> {
        return binding.edClientPass.editorActions { event -> event == EditorInfo.IME_ACTION_DONE }
    }

    override fun registerLoginEvent() {
        if (fromForgotPassPref.get())
            loginUiEvents.onNext(LoginUiEvent.LoginVerify)
        else
            loginUiEvents.onNext(LoginUiEvent.LoginSubmit)
    }

    override fun onSubmitClicked(): Observable<LoginUiEvent.LoginSubmit> {
        return loginUiEvents.ofType()
    }

    override fun onForgotPasswordClicked(): Observable<Unit> {
        return binding.txtForgotPass.clicks()
    }

    override fun getUserName(): String {
        return binding.edClientCode.text.toString().trim()
    }

    override fun getPassword(): String {
        return binding.edClientPass.text.toString()
    }

    override fun getUserType(): String {
        return UserType.TRADING.label
    }

    override fun getClientCode(): String {
        return ""
    }

    override fun setRetainedUser(user: String) {
        binding.edClientCode.setText(user)
    }

    override fun postNavigationEvent(type: LoginType) {
        navigationEvent().onNext(type)
    }

    override fun renderLogin(uiModel: UiModel<LoginResponse>) {
        binding.progressBar.toggleProgressBar(uiModel.inProgress)

        uiModel.onFailure { error ->
            showOnFailurePopup(error)
        }

        uiModel.onSuccess {
            clearPref()
        }
    }

    override fun renderProceed(uiModel: UiModel<String>) {
        binding.progressBar.toggleProgressBar(uiModel.inProgress)

        uiModel.onFailure { error ->
            showOnFailurePopup(error)
        }

        uiModel.onSuccess {
            clearPref()
        }
    }

    override fun showForgotPasswordBottomSheet() {
        ForgotPasswordBottomSheetFragment()
            .apply {
                isCancelable = false
                arguments = bundleOf()
            }
            .show(childFragmentManager, "ForgotPasswordBottomSheetFragment")
    }

}