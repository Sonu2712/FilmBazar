package com.film.bazar.home_ui

import com.film.app.core.base.BaseView
import com.film.commons.data.UiModel

interface HomeView : BaseView {
    fun renderWelcome(uiModel: UiModel<String>)
}