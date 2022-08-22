package com.film.bazar.coreui.appcoreui.textview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.film.app.core.utils.showDatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatePickerTextView : AppCompatTextView {
  private lateinit var dateBounds: Pair<Date?, Date?>
  private lateinit var displayFormatter: DateFormat
  var listener: Listener? = null
  private var selectedDate: Date? = null

  constructor(context: Context) : super(context)

  constructor(
      context: Context,
      attrs: AttributeSet
  ) : super(context, attrs)

  override fun onFinishInflate() {
    super.onFinishInflate()
    init()
  }

  private fun init() {
    setDateBounds(Date(), null)
    setDisplayDateFormat("dd-MMM-yyyy")
    setOnClickListener { _ ->
      val withDate = this.selectedDate ?: dateBounds.first
      context.showDatePicker(
          callback = { this.setSelectedDate(it) },
          setDate = withDate,
          minDate = dateBounds.first,
          maxDate = dateBounds.second
      )
    }
  }

  fun setDateSelectionListener(listener: Listener) {
    this.listener = listener
  }

  fun setDisplayDateFormat(format: String) {
    displayFormatter = SimpleDateFormat(format, Locale.ENGLISH)
  }

  fun setDate(date: Date) {
    selectedDate = date
    text = displayFormatter.format(date)
  }

  fun getSelectedDate(): Date? {
    return selectedDate
  }

  private fun setSelectedDate(selectedDate: Date) {
    listener?.onDateSelected(selectedDate)
    setDate(selectedDate)
  }

  fun setDateBounds(
      minDate: Date?,
      maxDate: Date?
  ) {
    dateBounds = Pair(minDate, maxDate)
  }

  interface Listener {
    fun onDateSelected(date: Date)
  }
}
