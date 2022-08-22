package com.film.login_ui.base

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import com.film.commons.rx.addTo
import com.film.commons.rx.safeMap
import com.film.login_ui.LoginEventProvider
import com.film.login_ui.LoginType
import com.film.login_ui.R
import com.film.login_ui.core.LoginActivity
import com.film.login_ui.customer.CustomerLoginFragment
import com.film.login_ui.databinding.FragmentLoginBaseBinding
import com.film.login_ui.nav.LoginConstants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LoginBaseFragment : BaseFragment(), LoginPagerView, LoginEventProvider {

    lateinit var binding: FragmentLoginBaseBinding
    protected val navigation: PublishSubject<LoginType> = PublishSubject.create()

    @Inject
    lateinit var presenter: LoginBasePresenter

    lateinit var loginType: LoginType

    override fun getLayout(): Int {
        return R.layout.fragment_login_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBaseBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        extractArgument(arguments)
        presenter.start()
    }

    private fun extractArgument(bundle: Bundle?) {
        loginType = LoginUiMapper.handleExternalNavigation(
            bundle?.getString(LoginActivity.ARG_PAGE_ID)
                ?: LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN
        )
        val navigateUser = LoginUiMapper.handleInternalNavigation(loginType, requireContext())
        render(loginType to navigateUser)
    }

    private fun setupViews() {
        setAppTitle("")

        navigationEvent.safeMap {
            it to LoginUiMapper.handleInternalNavigation(
                loginType = it,
                context = requireContext()
            )
        }.subscribe { render(it) }
            .addTo(disposable)
    }

    private fun render(pair: Pair<LoginType, Pair<LoginPageDataModel, LoginPageDataModel>>) {
        loginType = pair.first
        loadFragment(pair.first.id)
        loadActions(pair.second)
    }

    private fun loadFragment(pageId: Int) {
        val fragment: BaseFragment = when (pageId) {
            0 -> CustomerLoginFragment().apply {
                this.arguments = this@LoginBaseFragment.arguments
            }
            1 -> CustomerLoginFragment()
            2 -> CustomerLoginFragment()
            3 -> CustomerLoginFragment()
            else -> CustomerLoginFragment()
        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flLayout, fragment)
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.commit()
    }

    private fun loadActions(pair: Pair<LoginPageDataModel, LoginPageDataModel>) {
        binding.showFirstRow(pair.first)
        binding.showSecondRow(pair.second)
    }

    override fun onBackPressed(): Boolean {
        if (loginType.id == 0) {
            return super.onBackPressed()
        } else {
            navigation.onNext(LoginType.Customer())
        } // Setting First Page
        return true
    }

    private fun FragmentLoginBaseBinding.showFirstRow(model: LoginPageDataModel) {
        llRow1.visibility = if (model.visible) View.VISIBLE else View.GONE
        tvRow1Label.text = model.key
        tvRow1Action.text = model.value
        tvRow1Action.tag = model.type
        ivInfo.visibility = if (model.showInfo) View.VISIBLE else View.GONE
    }

    private fun FragmentLoginBaseBinding.showSecondRow(model: LoginPageDataModel) {
        llRow2.visibility = if (model.visible) View.VISIBLE else View.GONE
        tvRow2Label.text = model.key
        tvRow2Action.text = model.value
        tvRow2Action.tag = model.type
    }

    override fun onActionSelected(loginType: LoginType) {
        navigation.onNext(loginType)
    }

    override val navigationEvent: PublishSubject<LoginType>
        get() = navigation

    override fun onFirstRowActionClicked(): Observable<Unit> {
        return binding.tvRow1Action.clicks()
    }

    override fun onSecondRowActionClicked(): Observable<Unit> {
        return binding.tvRow2Action.clicks()
    }

    override fun getFirstRowAction(): LoginType {
        return binding.tvRow1Action.tag as LoginType
    }

    override fun getSecondRowAction(): LoginType {
        return binding.tvRow2Action.tag as LoginType
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }
}
