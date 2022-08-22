package com.film.bazar.coreui.helper

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.film.bazar.coredomain.OrderAction
import com.film.bazar.coreui.R

fun TextView.setTextColor(context: Context, action: OrderAction) {
    setTextColor(
        ContextCompat.getColor(
            context,
            if (action.isBuy()) R.color.text_color_buy else R.color.text_color_sell
        )
    )
}

const val CAROUSEL = "Carousel"
const val LINEAR = "Linear"
const val OTHER = "Other"
const val LINEARSMALL = "LinearSmall"
