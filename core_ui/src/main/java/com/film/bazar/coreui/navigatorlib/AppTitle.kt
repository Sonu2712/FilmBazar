package com.film.bazar.coreui.navigatorlib

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.io.Serializable

class AppTitle private constructor(
    @JvmField val titleStr: String?,
    @param:StringRes @JvmField val title: Int,
    @param:DrawableRes @JvmField val icon: Int
) : Serializable {

  val isEmpty: Boolean
    get() = this == EMPTY

  fun hasTitle(): Boolean {
    return title > 0 || titleStr != null
  }

  fun hasIcon(): Boolean {
    return icon > 0
  }

  fun hasStringRes(): Boolean {
    return title > 0
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AppTitle

    if (titleStr != other.titleStr) return false
    if (title != other.title) return false
    if (icon != other.icon) return false

    return true
  }

  override fun hashCode(): Int {
    var result = titleStr?.hashCode() ?: 0
    result = 31 * result + title
    result = 31 * result + icon
    return result
  }

  override fun toString(): String {
    return "AppTitle(titleStr=$titleStr, title=$title, icon=$icon)"
  }

  companion object {
    @JvmField
    val EMPTY = AppTitle(null, 0, 0)

    @JvmStatic
    fun withTitle(@StringRes title: Int): AppTitle {
      return AppTitle(null, title, 0)
    }

    @JvmStatic
    fun withTitle(title: String): AppTitle {
      return AppTitle(title, 0, 0)
    }

    @JvmStatic
    fun withIcon(@StringRes title: Int, @DrawableRes icon: Int): AppTitle {
      return AppTitle(null, title, icon)
    }

    @JvmStatic
    fun withIcon(title: String, @DrawableRes icon: Int): AppTitle {
      return AppTitle(title, 0, icon)
    }
  }
}
