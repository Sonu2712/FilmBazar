package com.film.bazar.logger

data class InvestorLog(
    val dateTime: String,
    val level: Int,
    val tag: String,
    val message: String
)
