package com.film.bazar.coreui.appcoreui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import com.film.bazar.coreui.R

/**
 * A layout that draws something in the insets passed to [.fitSystemWindows], i.e. the
 * area above UI chrome (status and navigation bars, overlay action bars).
 *
 *
 * Unlike the `ScrimInsetsFrameLayout` in the design support library, this variant does not
 * consume the insets.
 */
class NonConsumingScrimInsetsFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
  private var insetForeground: Drawable? = null
  private var insets: Rect? = null
  private val tempRect = Rect()

  init {
    context.obtainStyledAttributes(attrs, R.styleable.NonConsumingScrimInsetsFrameLayout, defStyle, 0).run {
      insetForeground = getDrawable(R.styleable.NonConsumingScrimInsetsFrameLayout_insetForeground)
      recycle()
    }
    setWillNotDraw(true)
  }

  override fun fitSystemWindows(insets: Rect): Boolean {
    this.insets = Rect(insets)
    setWillNotDraw(insetForeground == null)
    ViewCompat.postInvalidateOnAnimation(this)
    return false // Do not consume insets.
  }

  override fun draw(canvas: Canvas) {
    super.draw(canvas)
    val insets = this.insets ?: return
    val insetForeground = insetForeground ?: return
    val width = width
    val height = height
    val sc = canvas.save()
    canvas.translate(scrollX.toFloat(), scrollY.toFloat())

    // Top
    tempRect.set(0, 0, width, insets.top)
    insetForeground.bounds = tempRect
    insetForeground.draw(canvas)

    // Bottom
    tempRect.set(0, height - insets.bottom, width, height)
    insetForeground.bounds = tempRect
    insetForeground.draw(canvas)

    // Left
    tempRect.set(0, insets.top, insets.left, height - insets.bottom)
    insetForeground.bounds = tempRect
    insetForeground.draw(canvas)

    // Right
    tempRect.set(width - insets.right, insets.top, width, height - insets.bottom)
    insetForeground.bounds = tempRect
    insetForeground.draw(canvas)

    canvas.restoreToCount(sc)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    insetForeground?.callback = this
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    insetForeground?.callback = null
  }
}
