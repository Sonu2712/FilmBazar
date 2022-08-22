@file:JvmName("Injector")

package com.film.app.core.utils

import android.content.Context

// Explicitly doing a custom service.
@Suppress("UNCHECKED_CAST")
fun <T> Context.obtain(componentClass: Class<T>): T {
  return getSystemService(componentClass.name) as T
}

fun <T> matchesService(name: String, componentName: Class<T>): Boolean {
  return componentName.name == name
}