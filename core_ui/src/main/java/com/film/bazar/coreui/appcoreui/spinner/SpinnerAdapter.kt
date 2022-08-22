package com.film.bazar.coreui.appcoreui.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.film.bazar.coreui.R
import com.film.bazar.domaincore.SpinnerData

class SpinnerAdapter<T : SpinnerData>(
  context: Context,
  private val spinnerData: List<T>,
  private val layoutResource: Int = android.R.layout.simple_spinner_dropdown_item,
  private val textViewResourceId: Int = R.layout.row_spinner_simple
) : ArrayAdapter<T>(context, layoutResource, spinnerData) {

  override fun getView(
    position: Int,
    convertView: View?,
      parent: ViewGroup
  ): View {
    return createViewFromResource(
      position, parent, textViewResourceId,
      LayoutInflater.from(context)
    )
  }

  override fun getDropDownView(
    position: Int,
    convertView: View?,
    parent: ViewGroup?
  ): View {
    return createDropDownViewFromResource(
      position,
      convertView,
      parent,
      LayoutInflater.from(context)
    )
  }

  private fun createViewFromResource(
    position: Int,
    parent: ViewGroup?,
    res: Int,
    layoutInflater: LayoutInflater
  ): View {
    val view: TextView = layoutInflater.inflate(res, parent, false) as TextView
    view.text = context.getText(spinnerData[position].labelRes)
    return view
  }

  private fun createDropDownViewFromResource(
    position: Int,
    convertView: View?,
    parent: ViewGroup?,
    layoutInflater: LayoutInflater
  ): View {
    val view: TextView = convertView as TextView? ?: layoutInflater.inflate(
      layoutResource,
      parent,
      false
    ) as TextView
    view.text = context.getText(spinnerData[position].labelRes)
    return view
  }
}
