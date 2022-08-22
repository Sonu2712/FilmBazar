package com.film.bazar.coreui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.film.bazar.coreui.R

class PageProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.layout_page_progressbar, this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        visibility = View.GONE
    }

    fun toggleProgress(toggle: Boolean) {
        if (toggle) showProgress() else hideProgress()
    }

    fun showProgress() {
        visibility = View.VISIBLE
    }

    fun hideProgress() {
        visibility = View.GONE
    }
}
