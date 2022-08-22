package com.film.bazar.coreui.navigatorlib

import timber.log.Timber
import java.util.ArrayList

/**
 * A Fragment transition helper that cache's the fragment navigation
 * called after Activity onPause, and replays it once Activity onResume is done with
 * http://stackoverflow.com/questions/8040280/how-to-handle-handler-messages-when-activity-fragment-is-paused
 */

abstract class FragmentTransitionHelper {
  /**
   * Message Queue Buffer
   */
  private val fragmentQueue = ArrayList<FragmentNavigationEntry>()
  /**
   * Flag indicating the pause state
   */
  var isPaused: Boolean = false
    private set

  /**
   * Resume the handler
   */
  fun resume() {
    isPaused = false
    var i = 0
    while (fragmentQueue.size > 0) {
      Timber.d("Processing the queue : %d", ++i)
      val msg = fragmentQueue.removeAt(0)
      handleMessage(msg)
    }
  }

  /**
   * Pause the handler
   */
  fun pause() {
    isPaused = true
  }

  /**
   * Notification that the message is about to be stored as the activity is
   * paused. If not handled the message will be saved and replayed when the
   * activity resumes.
   *
   * @param message the message which optional can be handled
   * @return true if the message is to be stored
   */
  protected abstract fun storeMessage(message: FragmentNavigationEntry): Boolean

  /**
   * Notification message to be processed. This will either be directly from
   * handleMessage or played back from a saved message when the activity was
   * paused.
   *
   * @param message the message to be handled
   */
  protected abstract fun processMessage(message: FragmentNavigationEntry)

  fun navigateTo(message: FragmentNavigationEntry) {
    handleMessage(message)
  }

  /**
   * it takes a FragmentNavigationEntry param with necessary data about
   * navigation, if the activity is paused, the message is stored.
   * if the message is not stored, it is dropped altogether.
   *
   * @param msg a message containing navigation description.
   */
  private fun handleMessage(msg: FragmentNavigationEntry) {
    if (isPaused) {
      if (storeMessage(msg)) {
        fragmentQueue.add(msg)
      }
    } else {
      processMessage(msg)
    }
  }
}