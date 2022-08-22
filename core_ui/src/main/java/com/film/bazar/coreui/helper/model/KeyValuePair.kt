package com.film.bazar.coreui.helper.model

import androidx.annotation.StringRes

data class KeyValuePair(
    @JvmField val key: String,
    @JvmField val keyResId: Int = -1,
    @JvmField val value: CharSequence
) {
    constructor(
        key: String,
        value: String
    ) : this(key, -1, value)

    constructor(@StringRes keyResId: Int, value: CharSequence) : this("", keyResId, value)
}
