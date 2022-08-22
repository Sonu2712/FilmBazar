package com.film.commons.appinfo

data class AppInfo(
    @JvmField val name: String,
    @JvmField val os: String,
    @JvmField val osVersion: String,
    @JvmField val versionName: String,
    @JvmField val versionCode: Int,
    @JvmField val sha: String,
    @JvmField val appMarket: String,
    @JvmField val source: String
) {
  override fun toString(): String {
      return "$os/$osVersion/$name/${versionName}_$versionCode/$sha"
  }
}