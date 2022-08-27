package com.film.bazar.coreui.appcoreui.dialog

import android.app.Activity
import androidx.annotation.StringRes
import com.film.bazar.coreui.R
import com.film.bazar.flashbar.Flashbar
import com.film.bazar.flashbar.anim.FlashAnim

fun Activity.showDialogForSuccess(
    message: String,
    titleRes: String = getString(R.string.alert_dialog_title)
) {
    val dialog = Flashbar.Builder(this)
        .backgroundColorRes(R.color.background_color_confirmation)
        .title(titleRes)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .gravity(Flashbar.Gravity.TOP)
        .dismissOnTapOutside(true)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_warning_24dp)
        .showIcon()
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .positiveActionText(getString(R.string.button_label_ok))
        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .build()
    dialog.show()
}

fun Activity.buildCustomDialogOrToast(): Flashbar.Builder {
    return Flashbar.Builder(this)
}

fun Activity.showToastForSuccess(
    message: String,
    @StringRes titleRes: Int = R.string.alert_dialog_title
) {
    Flashbar.Builder(this)
        .backgroundColorRes(R.color.background_color_success)
        .title(titleRes)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .duration(Flashbar.DURATION_LONG)
        .gravity(Flashbar.Gravity.TOP)
        .dismissOnTapOutside(true)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_success)
        .showIcon()
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .build().show()

}

fun Activity.showDialogForError(
    message: String,
    titleRes: String = getString(R.string.alert_dialog_title)
) {
    val dialog = Flashbar.Builder(this)
        .backgroundColorRes(R.color.background_color_error)
        .title(titleRes)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .gravity(Flashbar.Gravity.TOP)
        .dismissOnTapOutside(true)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_error_24dp)
        .showIcon()
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .positiveActionText(getString(R.string.button_label_ok))
        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .build()
    dialog.show()
}

fun Activity.showDialogForError(
    @StringRes message: Int,
    @StringRes titleRes: Int = R.string.alert_dialog_title
) {
    showDialogForError(
        message = getString(message),
        titleRes = getString(titleRes)
    )
}

fun Activity.showToastForError(
    message: String,
    @StringRes titleRes: Int = R.string.alert_dialog_title
) {
    val toast = Flashbar.Builder(this)
        .backgroundColorRes(R.color.background_color_error)
        .title(titleRes)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .duration(Flashbar.DURATION_LONG)
        .gravity(Flashbar.Gravity.TOP)
        .dismissOnTapOutside(true)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_error_24dp)
        .showIcon()
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .build()
    toast.show()
}

fun Activity.showToastForWarning(
    message: String,
    @StringRes titleRes: Int = R.string.alert_dialog_title,
    isBackPress: Boolean = false
) {
    Flashbar.Builder(this)
        .backgroundColorRes(R.color.green)
        .title(titleRes)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .duration(Flashbar.DURATION_LONG)
        .gravity(Flashbar.Gravity.TOP)
        .dismissOnTapOutside(true)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_green_tick)
        .showIcon()
        .isCancelable(isBackPress)
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .build().show()
}

fun Activity.showDialogForConfirmation(
    message: String,
    positiveButton: String = getString(R.string.button_label_ok),
    negativeButton: String? = null,
    title: String = getString(R.string.alert_dialog_title),
    cancelable: Boolean = true,
    action: (Boolean) -> Unit
) {
    val dialog = Flashbar.Builder(this)
        .backgroundColorRes(R.color.green)
        .title(title)
        .titleColorRes(R.color.white_text_color)
        .message(message)
        .messageColorRes(R.color.white_text_color)
        .gravity(Flashbar.Gravity.TOP)
        .enterAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
        )
        .exitAnimation(
            FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
        )
        .icon(R.drawable.ic_warning_24dp)
        .showIcon()
        .iconAnimation(
            FlashAnim.with(this)
                .animateIcon()
                .pulse()
                .alpha()
                .duration(750)
                .accelerate()
        )
        .positiveActionText(positiveButton)
        .negativeActionText(negativeButton)
        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                action(true)
                bar.dismiss()
            }
        })
        .negativeActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                action(false)
                bar.dismiss()
            }
        })
        .positiveActionTextColorRes(R.color.white_text_color)
        .negativeActionTextColorRes(R.color.white_text_color)
        .dismissOnTapOutside(cancelable)
        .showOverlay()
        .overlayBlockable()
        .build()
    dialog.show()
}
