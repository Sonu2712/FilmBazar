package com.film.login_ui.base

import android.content.Context
import com.film.login_ui.LoginType
import com.film.login_ui.R
import com.film.login_ui.nav.LoginConstants

object LoginUiMapper {

    fun handleExternalNavigation(@LoginConstants pageId: String): LoginType {
        return when (pageId) {
            LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN -> {
                LoginType.Customer()
            }
            LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT -> {
                LoginType.OpenAccount()
            }
            LoginConstants.NAVIGATE_TO_FORGOT_PASSWORD -> {
                LoginType.Forgot()
            }
            LoginConstants.NAVIGATE_TO_GUEST_LOGIN -> {
                LoginType.Guest()
            }
            else -> LoginType.Customer()
        }
    }

    fun handleInternalNavigation(
        loginType: LoginType,
        context: Context
    ): Pair<LoginPageDataModel, LoginPageDataModel> {
        return when (loginType) {
          is LoginType.Customer -> {
            LoginPageDataModel.EMPTY to LoginPageDataModel.EMPTY
          }
            is LoginType.Guest -> {
                LoginPageDataModel.EMPTY to getViewModel(
                    context.getString(R.string.lbl_don_t_have_an_account),
                    context.getString(R.string.lbl_open_account),
                    LoginType.OpenAccount(),
                    true
                )
            }
            is LoginType.OpenAccount -> {
                LoginPageDataModel.EMPTY to getViewModel(
                    context.getString(R.string.lbl_name_already_a_user),
                    context.getString(R.string.lbl_name_login),
                    LoginType.Customer(),
                    true
                )
            }
            is LoginType.Forgot -> {
                LoginPageDataModel.EMPTY to LoginPageDataModel.EMPTY
            }
        }
    }

    private fun getViewModel(
        key: String,
        value: String,
        type: LoginType,
        visible: Boolean,
        showInfo: Boolean = false
    ): LoginPageDataModel {
        return LoginPageDataModel(
            key = key,
            value = value,
            type = type,
            visible = visible,
            showInfo = showInfo
        )
    }
}