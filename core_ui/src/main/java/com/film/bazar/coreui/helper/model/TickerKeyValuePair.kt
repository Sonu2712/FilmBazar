package com.film.bazar.coreui.helper.model

import androidx.annotation.StringRes

data  class TickerKeyValuePair(val key:String, @JvmField val keyResId: Int = -1, val value : TickerTextInfo) {
    constructor(
        key: String,
        value: TickerTextInfo
    ) : this(key, -1, value)

    constructor(@StringRes keyResId: Int, value: TickerTextInfo) : this("", keyResId, value)
}


data class TickerTextInfo(
    @JvmField val text: String,
    @JvmField val isTickerTextEnabled:Boolean,
    @JvmField val isTextColorEnabled: Boolean,
    @JvmField val isAdjacentImageEnabled: Boolean,
    @JvmField val tickerValues: TickerValues? = null
)

data class TickerValues(@JvmField val change: String, @JvmField val changePer: String, @JvmField val isPositive: Boolean)
