package com.film.bazar.dashboard

import android.os.Bundle

data class DashboardHeader(
    @JvmField val title: String,
    @JvmField val screens: List<DashboardData>
)

data class DashboardData(
    @JvmField val title: String,
    @JvmField val pageId: String,
    @JvmField val bundle: Bundle = Bundle.EMPTY
)
