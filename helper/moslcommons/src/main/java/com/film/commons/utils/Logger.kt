package com.film.commons.utils

import java.util.logging.Level

interface Logger {
  fun log(message: String)

  companion object {
    internal val platformLogger = java.util.logging.Logger.getLogger("MoslApp")
    val DEFAULT: Logger = object : Logger {
      override fun log(message: String) {
        platformLogger.log(Level.INFO, message)
      }
    }
  }
}