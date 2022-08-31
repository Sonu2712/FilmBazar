package com.film.bazar.profile.helpsupport

import com.film.app.core.base.BaseView
import com.film.bazar.domain.drawermenu.profile.HelpSupportQuestions
import com.film.bazar.profile.ProfileUiEvent
import com.film.commons.data.UiModel
import io.reactivex.rxjava3.core.Observable

interface HelpSupportView : BaseView {
    fun render(uiModel: UiModel<List<HelpSupportQuestions>>)
    fun onItemClicked(): Observable<ProfileUiEvent.OnItemClicked>
    fun onWritUsClicked(): Observable<Unit>
    fun onCallClicked(): Observable<ProfileUiEvent.CallUS>
    fun callToTradePopup()
    fun onChatClicked(): Observable<ProfileUiEvent.ChatWithUS>
    fun showMessage(msg: String)
}