package com.film.app.core.base

import com.film.app.core.events.DataAction
import io.reactivex.rxjava3.core.Observable

/**
 * Created by apple on 17/03/18.
 */
interface BaseView {
  fun onDataAction(): Observable<DataAction>
  fun isDataEmpty(): Boolean
}