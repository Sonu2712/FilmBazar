@file:JvmName("Dialogs")

package com.film.app.core.utils

import android.app.DatePickerDialog
import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.film.app.core.R
import java.util.Calendar
import java.util.Date

fun Context.showDatePicker(
    callback: (Date) -> Unit,
    setDate: Date?,
    minDate: Date?,
    maxDate: Date?
): DatePickerDialog {
  val now = Calendar.getInstance()
  if (setDate != null) {
    now.time = setDate
  }
  val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
    val cal = Calendar.getInstance()
    cal.clear()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, monthOfYear)
    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    callback.invoke(cal.time)
  }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
  val datePicker = datePickerDialog.datePicker
  if (minDate != null) {
    datePicker.minDate = minDate.time
  }
  if (maxDate != null) {
    datePicker.maxDate = maxDate.time
  }
  datePickerDialog.show()
  return datePickerDialog
}

@JvmOverloads
fun Context.alert(
    message: String,
    title: String? = getString(R.string.alert_dialog_title)
): AlertDialog {
  return MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setMessage(message)
      .setPositiveButton(R.string.button_label_ok, null)
      .show()
}

@JvmOverloads
fun Context.alert(
    @StringRes message: Int,
    @StringRes titleRes: Int = R.string.alert_dialog_title
): AlertDialog {
  return MaterialAlertDialogBuilder(this)
      .setTitle(titleRes)
      .setMessage(message)
      .setPositiveButton(R.string.button_label_ok, null)
      .show()
}

@JvmOverloads
fun Context.actionableAlert(
    message: String,
    cancelable: Boolean = false,
    positiveButton: Int = R.string.button_label_ok,
    callback: () -> Unit
): AlertDialog {
  val dialog = MaterialAlertDialogBuilder(this)
      .setTitle(R.string.alert_dialog_title)
      .setMessage(message)
      .setCancelable(cancelable)
      .setPositiveButton(positiveButton) { _, _ -> callback.invoke() }
      .show()
  dialog.setCanceledOnTouchOutside(cancelable)
  return dialog
}

@JvmOverloads
fun Context.actionableAlert(
    message: Int,
    cancelable: Boolean = false,
    positiveButton: Int = R.string.button_label_ok,
    callback: () -> Unit
): AlertDialog {
  val dialog = MaterialAlertDialogBuilder(this)
      .setTitle(R.string.alert_dialog_title)
      .setMessage(message)
      .setCancelable(cancelable)
      .setPositiveButton(positiveButton) { _, _ -> callback.invoke() }
      .show()
  dialog.setCanceledOnTouchOutside(cancelable)
  return dialog
}

@JvmOverloads
fun Context.showConfirmation(
    message: Int,
    cancelable: Boolean = false,
    title: Int = R.string.alert_dialog_title,
    positiveButton: Int = R.string.button_label_ok,
    negativeButton: Int = R.string.button_label_cancel,
    callback: (Boolean) -> Unit
): AlertDialog {
  val dialog = MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setMessage(message)
      .setCancelable(cancelable)
      .setPositiveButton(positiveButton) { _, _ -> callback.invoke(true) }
      .setNegativeButton(negativeButton) { _, _ -> callback.invoke(false) }
      .show()
  dialog.setCanceledOnTouchOutside(cancelable)
  return dialog
}

@JvmOverloads
fun Context.showConfirmation(
    message: String,
    cancelable: Boolean = false,
    title: String = getString(R.string.alert_dialog_title),
    positiveButton: Int = R.string.button_label_ok,
    negativeButton: Int = R.string.button_label_cancel,
    callback: (Boolean) -> Unit
): AlertDialog {
  val dialog = MaterialAlertDialogBuilder(this)
      .setTitle(title)
      .setMessage(message)
      .setCancelable(cancelable)
      .setPositiveButton(positiveButton) { _, _ -> callback.invoke(true) }
      .setNegativeButton(negativeButton) { _, _ -> callback.invoke(false) }
      .show()
  dialog.setCanceledOnTouchOutside(cancelable)
  return dialog
}