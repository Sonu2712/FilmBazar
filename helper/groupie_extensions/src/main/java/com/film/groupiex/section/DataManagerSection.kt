package com.film.groupiex.section

import android.view.View.OnClickListener
import com.film.app.core.error.ErrorModel
import com.film.app.core.error.getErrorOnFailure
import com.film.app.core.events.DataAction
import com.film.groupiex.items.ErrorMessageItem
import com.film.groupiex.items.InlineProgressItem
import com.xwray.groupie.Group
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import io.reactivex.rxjava3.subjects.PublishSubject

class DataManagerSection @JvmOverloads constructor(
    private val retryListener: OnClickListener,
    private val progressItem: Item<*> = InlineProgressItem()
) : Section() {
  var wrapErrorView: Boolean = false
  var retrySubject: PublishSubject<DataAction>? = null
  var isDataEmpty: Boolean = true
    private set

  fun toggleProgress(inProgress: Boolean) {
    if (inProgress) {
      if (getPosition(progressItem) == -1) {
        add(0, progressItem)
      }
    } else {
      remove(progressItem)
    }
  }

  fun setContent(group: Group) {
    setContent(listOf(group))
  }

  fun setContent(groups: Collection<Group>) {
    isDataEmpty = false
    if (groups.isEmpty()) {
      showEmptyView(ErrorModel.noDataAvailable())
    } else {
      update(groups)
      removePlaceholder()
    }
  }

  fun clearContent() {
    update(emptyList())
    removePlaceholder()
  }

  fun clearErrorState() {
    removePlaceholder()
  }

  @JvmOverloads
  fun showError(
      throwable: Throwable,
      sectionId: Int = -1
  ) {
    showError(throwable.getErrorOnFailure(), sectionId)
  }

  @JvmOverloads
  fun showError(
      errorModel: ErrorModel,
      sectionId: Int = -1
  ) {
    this.isDataEmpty = true
    showEmptyView(errorModel, sectionId)
  }

  @JvmOverloads
  fun showEmptyView(errorModel: ErrorModel, sectionId: Int = -1) {
    showEmptyView(ErrorMessageItem(errorModel, retryListener, retrySubject, wrapErrorView, sectionId))
  }

  fun showEmptyView(placeHolder: Item<*>) {
    update(emptyList())
    setPlaceholder(placeHolder)
  }
}