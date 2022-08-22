@file:JvmName("TextViewUtils")

package com.film.bazar.coreui.appcoreui.textview

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.widget.TextViewCompat

var TextView.drawableEndRelative: Drawable?
  get() = TextViewCompat.getCompoundDrawablesRelative(this)[2]
  set(value) {
    val drawables = TextViewCompat.getCompoundDrawablesRelative(this)
    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, drawables[0], drawables[1],
        value, drawables[3])
  }

var TextView.drawableStartRelative: Drawable?
  get() = TextViewCompat.getCompoundDrawablesRelative(this)[0]
  set(value) {
    val drawables = TextViewCompat.getCompoundDrawablesRelative(this)
    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, value, drawables[1],
        drawables[2], drawables[3])
  }