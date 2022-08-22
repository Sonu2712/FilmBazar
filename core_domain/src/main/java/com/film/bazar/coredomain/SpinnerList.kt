package com.film.bazar.coredomain

data class SpinnerList<T>(
    @JvmField val entries: List<T>,
    @JvmField val selectedEntry: T? = entries.firstOrNull()
) {
    @JvmField
    val size = entries.size

    init {
        require(entries.isNotEmpty())
    }
}
