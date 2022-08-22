@file:JvmName("AdapterUtils")

package com.film.bazar.coreui.appcoreui.spinner

import android.widget.AdapterView.INVALID_POSITION
import android.widget.ArrayAdapter
import android.widget.Spinner

@JvmOverloads
fun <T> ArrayAdapter<T>.setSpinnerData(
    spinner: Spinner,
    data: Collection<T>,
    retainPosition: Boolean = false,
    forceDispatchItemSelection: Boolean = false
) {
  val dataSize = data.size

  val selectedPosition = spinner.selectedItemPosition
  val selectedItem = spinner.selectedItem

  if (dataSize > 0) {
    setNotifyOnChange(false)
    clear()
    addAll(data)
    notifyDataSetChanged()
  } else {
    clear()
    return
  }

  val newPosition: Int = if (retainPosition && dataSize > selectedPosition) selectedPosition else 0

  if (selectedPosition != newPosition) {
    spinner.setSelection(newPosition)
    return
  }

  val newItem = spinner.selectedItem
  if (selectedItem != newItem || forceDispatchItemSelection) {
    spinner.dispatchItemSelection(newPosition)
  }
}

@JvmOverloads
fun <T> ArrayAdapter<T>.setSpinnerDataWithSelection(
    spinner: Spinner,
    data: Collection<T>,
    selectedData: T?,
    forceDispatchItemSelection: Boolean = false
) {
  val dataSize = data.size

  val selectedPosition = spinner.selectedItemPosition
  val selectedItem = spinner.selectedItem

  if (dataSize > 0) {
    setNotifyOnChange(false)
    clear()
    addAll(data)
    notifyDataSetChanged()
  } else {
    clear()
    return
  }

  val newPosition: Int = if (data.contains(selectedData)) data.indexOf(selectedData) else 0

  if (selectedPosition != newPosition) {
    spinner.setSelection(newPosition)
    return
  }

  val newItem = spinner.selectedItem
  if (selectedItem != newItem || forceDispatchItemSelection) {
    spinner.dispatchItemSelection(newPosition)
  }
}

fun <T> ArrayAdapter<T>.getItems(): List<T> {
  val oldData = mutableListOf<T>()
  for (i in 0 until count) {
    getItem(i)?.let {
      oldData.add(it)
    }
  }
  return oldData.toList()
}

fun Spinner.dispatchItemSelectionForUnchangedPosition(
    currSelection: Int,
    newSelection: Int
) {
  val onItemSelectedListener = onItemSelectedListener ?: return
  if (currSelection != INVALID_POSITION && currSelection == newSelection) {
    onItemSelectedListener.onItemSelected(this, selectedView, newSelection, selectedItemId)
  }
}

fun Spinner.dispatchItemSelection(newSelection: Int) {
  val onItemSelectedListener = onItemSelectedListener ?: return
  if (newSelection != INVALID_POSITION) {
    onItemSelectedListener.onItemSelected(this, selectedView, newSelection, selectedItemId)
  }
}