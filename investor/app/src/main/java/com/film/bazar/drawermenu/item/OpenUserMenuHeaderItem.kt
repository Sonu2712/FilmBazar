package com.film.bazar.drawermenu.item

import android.view.View
import com.film.bazar.R
import com.film.login_ui.nav.LoginConstants
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.IncludeOpenUserMenuHeaderBinding
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.domain.drawermenu.OtherMenu
import io.reactivex.rxjava3.subjects.PublishSubject

class OpenUserMenuHeaderItem(
    val menuSelectSubject: PublishSubject<AppMenu>
) : ViewBindingItem<IncludeOpenUserMenuHeaderBinding>() {

    override fun getLayout(): Int {
        return R.layout.include_open_user_menu_header
    }

    override fun initBinding(itemView: View): IncludeOpenUserMenuHeaderBinding {
        return IncludeOpenUserMenuHeaderBinding.bind(itemView)
    }

    override fun bind(viewBinding: IncludeOpenUserMenuHeaderBinding, position: Int) {
        viewBinding.btnOpenAccount.setOnClickListener {
            menuSelectSubject.onNext(
                OtherMenu(
                    LoginConstants.NAVIGATE_TO_OPEN_ACCOUNT
                )
            )
        }
        viewBinding.tvLogin.setOnClickListener { menuSelectSubject.onNext(
            OtherMenu(
            LoginConstants.NAVIGATE_TO_CUSTOMER_LOGIN)
        ) }
    }
}