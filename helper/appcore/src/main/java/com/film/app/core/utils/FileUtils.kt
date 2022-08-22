@file:JvmName("FileUtils")

package com.film.app.core.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

fun Context.readFile(fileName: String): CharSequence {
  return try {
    val file = assets.open(fileName)
    read(file)
  } catch (e: IOException) {
    ""
  }
}

private fun read(stream: InputStream): CharSequence {
  return try {
    val br = BufferedReader(InputStreamReader(stream))
    val buffer = StringBuilder()
    var line: String?
    do {
      line = br.readLine()
      if (line != null) {
        buffer.append(line).append('\n')
      }
    } while (line != null)
    buffer
  } catch (e: IOException) {
    ""
  }
}