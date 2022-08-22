package com.film.bazar.coredomain

data class LabelValuePair(
    @JvmField val label: String,
    @JvmField val labelResId: Int = -1,
    @JvmField val value: String
) {
    constructor(
        label: String,
        value: String
    ) : this(label, -1, value)

    constructor(labelResId: Int, value: String) : this("", labelResId, value)
}
