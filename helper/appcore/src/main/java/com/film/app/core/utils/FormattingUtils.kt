@file:JvmName("FormattingUtils")

package com.film.app.core.utils

import java.text.DecimalFormat
import java.util.*

@JvmField
internal val RoundOffCommaDecorator = DecimalFormat("##,##,##,##,###")

fun Int.toFormattedValue(): String {
    return RoundOffCommaDecorator.format(this)
}

fun Double.toFormattedValue(): String {
    return RoundOffCommaDecorator.format(this)
}

fun Float.toRoundedRupees(): String {
    return RoundOffCommaDecorator.format(this).prefixRupees()
}

fun Double.toRoundedRupees(): String {
    return RoundOffCommaDecorator.format(this).prefixRupees()
}

fun Float.to0Decimals(): String {
    return String.format(Locale.ENGLISH, "%.0f", this)
}

fun Float.to2Decimals(): String {
    return String.format(Locale.ENGLISH, "%.2f", this)
}

fun Float.to4Decimals(): String {
    return String.format(Locale.ENGLISH, "%.4f", this)
}

fun Double.to0Decimals(): String {
    return String.format(Locale.ENGLISH, "%.0f", this)
}

fun Double.to0Decimals(postfix: String = ""): String {
    val formattedString = String.format(Locale.ENGLISH, "%.0f", this)
    return if (postfix.isEmpty()) formattedString else formattedString + postfix
}

@JvmOverloads
fun Double.to2Decimals(postfix: String = ""): String {
    val formattedString = String.format(Locale.ENGLISH, "%.2f", this)
    return if (postfix.isEmpty()) formattedString else formattedString + postfix
}

fun Double.to4Decimals(): String {
    return String.format(Locale.ENGLISH, "%.4f", this)
}

inline fun String.prefixRupees(): String {
    return "₹ $this"
}