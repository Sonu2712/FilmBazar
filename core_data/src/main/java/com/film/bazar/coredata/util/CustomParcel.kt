package com.film.bazar.coredata.util

import android.os.Parcel

inline fun <T> Parcel.readNullable(reader: () -> T) =
    if (readInt() != 0) reader() else null

inline fun <T> Parcel.writeNullable(value: T?, writer: T.() -> Unit) {
    if (value != null) {
        writeInt(1)
        value.writer()
    } else {
        writeInt(0)
    }
}
