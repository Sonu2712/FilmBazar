package com.film.bazar.coreui.core

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.film.bazar.coreui.R
import com.film.bazar.coreui.appcoreui.base.BaseFragment
import com.film.bazar.coreui.navigatorlib.AppTitle

abstract class InvestorBaseFragment : BaseFragment(), ContainerChild {
    private var containerManager: ContainerManager? = null
    lateinit var coachMarksHelper: CoachMarksHelper
    private var mContainerState:ContainerState = ContainerState.DEFAULT
    override val containerState: ContainerState
        get() = mContainerState

    @CallSuper
    override fun onAttach(context: Context) {
        containerManager = context as ContainerManager
        coachMarksHelper = context as CoachMarksHelper
        super.onAttach(context)
    }

    @CallSuper
    override fun onDetach() {
        containerManager = null
        super.onDetach()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            mContainerState = savedInstanceState.getSerializable(CONTAINER_STATE) as? ContainerState
                ?: getInitialState()
            setToolbarView(getToolbarView())
        } else {
            mContainerState = getInitialState()
            if (parentFragment == null) {
                // Set activity state only if #this is in top level container.
                val initialState = getInitialState()
                setContainerState(
                    initialState.showBackButton,
                    initialState.showToolbar,
                    initialState.showBottomBar
                )
                setToolbarView(getToolbarView())
            }
        }
    }

    @MenuRes
    protected open fun getOptionsMenu(): Int = -1

    @Deprecated("Use getOptionsMenu()")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        menu.add(Menu.NONE, R.id.nav_feedback, 1, R.string.app_menu_title_send_feedback)
        inflater.inflate(getOptionsMenu(), menu)
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(CONTAINER_STATE, mContainerState)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            mContainerState = savedInstanceState.getSerializable(CONTAINER_STATE) as? ContainerState
                ?: getInitialState()
            setToolbarView(getToolbarView())
        }
    }

    protected fun setContainerState(
        showBackButton: Boolean = false,
        showToolbar: Boolean = true,
        showBottomBar: Boolean = true
    ) {
        mContainerState = mContainerState.copy(
            showBackButton = showBackButton,
            showToolbar = showToolbar,
            showBottomBar = showBottomBar
        )
        refreshContainer()
    }

    open fun getInitialState(): ContainerState {
        return ContainerState.DEFAULT
    }

    open fun getPageName(): String {
        return ""
    }

    open fun getEventName(): String {
        return ""
    }

    open fun getEventData(): Bundle {
        return Bundle()
    }

    protected fun setToolbarView(toolbarView: View?) {
        mContainerState = mContainerState.copy(toolbarView = toolbarView)
        refreshContainer()
    }

    open fun getToolbarView(): View? {
        return null
    }

    /**
     * Set title on the title bar.
     *
     * @param title the title to be set
     */
    protected fun setTitle(title: AppTitle) {
        mContainerState = mContainerState.copy(appTitle = title)
        refreshContainer()
    }

    protected fun setTitle(@StringRes pageTitle: Int) {
        setTitle(AppTitle.withTitle(pageTitle))
    }

    protected fun setTitle(pageTitle: String) {
        setTitle(AppTitle.withTitle(pageTitle))
    }

    private fun refreshContainer() {
        if (parentFragment == null) {
            containerManager?.setState(this.toString(), mContainerState)
        }
    }

    companion object {
        const val CONTAINER_STATE = "containerState"
    }
}
