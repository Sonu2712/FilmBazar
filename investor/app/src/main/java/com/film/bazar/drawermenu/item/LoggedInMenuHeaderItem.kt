package com.film.bazar.drawermenu.item

import android.view.View
import com.film.bazar.R
import com.film.bazar.constants.NavigationConstants
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.IncludeLoggedInMenuHeaderBinding
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.domain.drawermenu.OtherMenu
import com.film.bazar.appusercore.model.User
import io.reactivex.rxjava3.subjects.PublishSubject

class LoggedInMenuHeaderItem(
    private val user: User?,
    val menuSelectSubject: PublishSubject<AppMenu>
) : ViewBindingItem<IncludeLoggedInMenuHeaderBinding>() {

    override fun getLayout(): Int {
        return R.layout.include_logged_in_menu_header
    }

    override fun initBinding(itemView: View): IncludeLoggedInMenuHeaderBinding {
        return IncludeLoggedInMenuHeaderBinding.bind(itemView)
    }

    override fun bind(viewBinding: IncludeLoggedInMenuHeaderBinding, position: Int) {
        user?.let {
            if (it.userType.isGuest) {
                viewBinding.apply {
                    tvClientName.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                    tvInitial.visibility = View.GONE
                    ivAvatar.visibility = View.GONE
                    tvClientCode.visibility = View.GONE
                    tvClientName.text = it.userCode
                }
            } else {
                viewBinding.apply {
                    val context = root.context
                    tvInitial.visibility = View.GONE
                    ivAvatar.visibility = View.VISIBLE
                    tvClientCode.visibility = View.VISIBLE
                    tvInitial.text = it.initials
                    tvClientName.text = it.userName
                    tvClientCode.text =
                        context.getString(R.string.app_logged_in_client_code, it.userCode)
                }
            }
        }
    }
}