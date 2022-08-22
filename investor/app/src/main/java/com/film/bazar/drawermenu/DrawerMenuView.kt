package com.film.bazar.drawermenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.film.bazar.appuser.UserEvent
import com.film.app.core.prefs.IntegerPreference
import com.film.app.core.rx.applyUiModel
import com.film.app.core.utils.obtain
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.bazar.ApplicationComponent
import com.film.bazar.R
import com.film.annotations.DayOrNight
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.domain.drawermenu.AppMenu
import com.film.bazar.domain.drawermenu.AppMenus
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.drawermenu.item.*
import com.film.bazar.appusercore.model.UserType
import com.xwray.groupie.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DrawerMenuView(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val menuSelectSubject = PublishSubject.create<AppMenu>()
    private val selectableItemList = ArrayList<SelectableMenu>()
    @Inject
    lateinit var repository : MenuDataSourceRepository

    @Inject
    lateinit var userManager: UserManager

    @Inject
    @DayOrNight
    lateinit var dayOrNight: IntegerPreference
    private val rvDrawerMenu: RecyclerView
    private val expandableItems = ArrayList<BaseMenuExpandableItem>()
    private val disposable: CompositeDisposable
    private val menuSection: Section
    private var selectedMenuItem: Item<*>? = null
    private val itemExpansionListener = OnItemClickListener { item, _ ->
        for (expandableItem in expandableItems) {
            if (expandableItem != item) {
                expandableItem.collapse()
            }
        }
    }

    init {
        context.applicationContext.obtain(ApplicationComponent::class.java).inject(this)

        LayoutInflater.from(context).inflate(R.layout.view_drawer_menu, this)

        rvDrawerMenu = findViewById(R.id.rv_drawer_menu)

        menuSection = Section()

        rvDrawerMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = getItemAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        disposable = CompositeDisposable()

        val onUserChanged = userManager.onUserChanged()
            .distinctUntilChanged()
            .map { event ->
                if (event is UserEvent.LoggedIn) {
                    userManager.getUserType()
                } else {
                    UserType.OPEN_USER
                }
            }.startWithItem(userManager.getUserType())
            .share()

        onUserChanged
            .filter { userManager.isLoggedIn() }
            .switchMap { userType ->
            repository.getMenuItems()
                .compose(applyUiModel())
        }.subscribe(this::render)
            .addTo(disposable)
    }

    private fun getItemAdapter(): GroupAdapter<GroupieViewHolder> {
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        groupAdapter.add(menuSection)
        groupAdapter.setOnItemClickListener { item, _ ->
            if (item is SelectableMenu && item.getAppMenu().enabled) {
                menuSelectSubject.onNext((item).getAppMenu())
            }
        }
        return groupAdapter
    }

    override fun onDetachedFromWindow() {
        disposable.clear()
        super.onDetachedFromWindow()
    }

    fun onMenuClicked(): Observable<AppMenu> {
        return menuSelectSubject
    }

    fun selectItem(appMenu: AppMenu) {
        for (item in selectableItemList) {
            if (item.getAppMenu() == appMenu) {
                selectItem(item as Item<*>)
            }
        }
    }

    private fun selectItem(clickedItem: Item<*>) {
        if (clickedItem == selectedMenuItem) return
        selectedMenuItem = clickedItem
        for (item in selectableItemList) {
            item.setSelected(clickedItem == item)
        }
    }

    //TODO("Remove DarkModeMenu once app is stable")
    fun refreshHeader() {
        val userType = userManager.getUserType()
        if (userType == UserType.OPEN_USER) {
            menuSection.setHeader(OpenUserMenuHeaderItem(menuSelectSubject))
        } else {
            menuSection.setHeader(LoggedInMenuHeaderItem(userManager.getUser(), menuSelectSubject))
        }
    }

    internal fun render(uiModel: UiModel<AppMenus>) {
        uiModel.onFailure {
            Timber.e(it)
        }

        uiModel.onSuccess { appmenus ->
            rvDrawerMenu.apply {
                adapter = getItemAdapter()
            }
            val data = appmenus.baseMenu
            refreshHeader()
            val groups = ArrayList<Group>()
            selectableItemList.clear()
            for (baseMenu in data) {
                if (baseMenu.children.isEmpty()) {
                    if(baseMenu.label.isNotBlank()) {
                        val baseMenuItem = BaseMenuItem(baseMenu)
                        selectableItemList.add(baseMenuItem)
                        groups.add(baseMenuItem)
                    }
                } else {
                    val expandableItem = BaseMenuExpandableItem(baseMenu, itemExpansionListener)
                    expandableItems.add(expandableItem)

                    val childMenuItems = baseMenu.children.map(::ChildMenuItem)
                    selectableItemList.addAll(childMenuItems)
                    ExpandableGroup(expandableItem).apply {
                        addAll(childMenuItems)
                        groups.add(this)
                    }
                }
            }
            menuSection.update(groups)
            rvDrawerMenu.scrollToPosition(0)
        }
    }
}