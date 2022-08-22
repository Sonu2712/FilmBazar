package com.film.login_ui.helper

import android.text.InputFilter

var emojiFilter = InputFilter { source, start, end, dest, dstart, dend ->
    for (i in start until end) {
        val type = Character.getType(source[i])
        if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
            return@InputFilter ""
        }
    }
    null
}
