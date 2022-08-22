package com.film.app.core.events

/**
 * Created by apple on 17/03/18.
 */
sealed class DataAction {
  object Fetch : DataAction()
  object Refresh : DataAction()
  object Retry : DataAction()
  data class RetrySection(@JvmField val id: Int) : DataAction()
}