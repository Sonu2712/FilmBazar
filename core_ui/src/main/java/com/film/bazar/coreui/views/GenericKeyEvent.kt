package com.film.bazar.coreui.views

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.film.bazar.coreui.R

class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) :
    View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.edOTP1 && currentView.text.isEmpty()) {
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}