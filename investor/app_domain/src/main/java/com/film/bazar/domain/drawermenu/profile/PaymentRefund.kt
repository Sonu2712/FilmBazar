package com.film.bazar.domain.drawermenu.profile

data class HelpSupportQuestions(
    val questionId : Int,
    val questionLabel : String
)

data class AnswerValue(
    val id: Int,
    var selected: AnswerYesNo = AnswerYesNo.NOTHING,
    val key: String,
    val value: List<String>
)

sealed class AnswerYesNo {
    object YES : AnswerYesNo()
    object NO : AnswerYesNo()
    object NOTHING : AnswerYesNo()
}