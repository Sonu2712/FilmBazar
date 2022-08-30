package com.film.bazar.coreui.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.film.bazar.coreui.R
import com.film.bazar.coreui.navigatorlib.AppTitle
import java.util.*

class InvestorToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.style.Widget_MoslTheme_Toolbar
) : Toolbar(context, attrs, defStyle) {
    private var mCustomView: View? = null


    fun setAppTitle(appTitle: AppTitle) {
        if (appTitle.hasTitle()) {
            if (appTitle.hasStringRes() && appTitle.subTitle) {
                title = context.getString(appTitle.title)
                subtitle = "bazar"
            } else {
                title = appTitle.titleStr!!
                subtitle = ""
            }
        } else {
            title = ""
            subtitle = ""
        }
    }

    fun setCustomView(customView: View?) {
        if (mCustomView != null) {
            removeView(mCustomView)
        }
        if (customView != null) {
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            lp.gravity = GravityCompat.START or Gravity.VERTICAL_GRAVITY_MASK
            customView.layoutParams = lp
            addView(customView)
            mCustomView = customView
        }
    }

    fun setNavigationMode(goBack: Boolean = false, showNavigationIcon: Boolean = true) {
        if (goBack) {
            setTag(R.id.tool_bar_go_back, true)
            setNavigationIcon(R.drawable.ic_arrow_back_black)
            setTitleTextColor(ContextCompat.getColor(context, R.color.film_title_color))
            setPadding(12,12,0,12)
            setNavigationContentDescription(R.string.text_label_go_back)
        } else {
            setTag(R.id.tool_bar_go_back, false)
            setNavigationIcon(R.drawable.app_ic_film)
            setNavigationContentDescription(R.string.text_label_drawer_menu)
        }
        if (!showNavigationIcon) navigationIcon = null
    }

    fun isBackNavigation(): Boolean {
        return getTag(R.id.tool_bar_go_back) as? Boolean ?: false
    }
}
