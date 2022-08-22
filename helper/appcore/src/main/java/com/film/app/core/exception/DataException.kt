package com.film.app.core.exception

class DataException(val msg: String) : RuntimeException() {
  override fun toString(): String {
    return "DataException(msg='$msg')"
  }
}