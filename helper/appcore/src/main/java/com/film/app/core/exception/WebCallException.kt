package com.film.app.core.exception

open class WebCallException(val errorMessage: String) : Exception() {
  override fun toString(): String {
    return "WebCallException(errorMessage='$errorMessage')"
  }
}