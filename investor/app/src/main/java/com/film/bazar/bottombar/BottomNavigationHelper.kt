package com.film.bazar.bottombar
import android.view.Menu
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_HOME
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_MORE
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_PORTFOLIO
import com.film.bazar.constants.NavigationConstants.Companion.NAVIGATE_TO_WALLET
import com.film.bazar.domain.drawermenu.UBottomBarMenu
import com.film.bazar.home_ui.HomeFragment

interface BottomBarHelper {
    fun pageOpened(fragment: Fragment)
}

/*======Call after user changed or first time when app started*/
fun BottomNavigationView.setBottomBarItems(
    itemList: List<String>,
    bottomMenu : List<UBottomBarMenu>,
    startupPageId : String
) {
    val visibleIds = mutableListOf<Int>()
    menu.clear()
    val selectedBottomMenu = bottomMenu
        .filter { itemList.contains(it.id) || it.isDefault }

    val newSelectedBottomMenu = selectedBottomMenu.map { it }

    newSelectedBottomMenu.forEach { menuModel ->
        val menuId = menuModel.id.bottomBarIntMapper()
        menu.add(Menu.NONE, menuId, Menu.NONE, menuModel.label)
            .setIcon(menuModel.id.getIcon())
        visibleIds.add(menuId)
    }
    menu.setGroupCheckable(Menu.NONE, true, true)

    val selectedMenu =  newSelectedBottomMenu.firstOrNull { it.id == startupPageId }
    if (selectedMenu != null) {
        setChecked(startupPageId.bottomBarIntMapper())
    } else {
        clearSelection()
    }
    tag = visibleIds.toList()
}

/*========Call after tab changed======*/
fun BottomNavigationView.selectBottomMenuForPage(fragment: Fragment) {
    var selectedMenuId: Int? = null
    for (i in 0 until menu.size) {
        if (bottomNavigationCheckable(menu[i].itemId.bottomBarStringMapper(), fragment)) {
            selectedMenuId = menu[i].itemId
            break
        }
    }
    if (selectedMenuId != null) {
        setChecked(selectedMenuId)
    } else {
        clearSelection()
    }
}

fun BottomNavigationView.clearSelection() {
    menu.setGroupCheckable(Menu.NONE, false, true)
}

fun bottomNavigationCheckable(
    pageId: String,
    screen: Fragment
): Boolean {
    return when {
        (pageId == NAVIGATE_TO_HOME &&
                screen is HomeFragment) -> true
        (pageId == NAVIGATE_TO_PORTFOLIO) -> true
        (pageId == NAVIGATE_TO_WALLET) -> true
        (pageId == NAVIGATE_TO_MORE) -> true
         else -> false
    }
}

private fun BottomNavigationView.setChecked(tab: Int): Boolean {
    menu.setGroupCheckable(Menu.NONE, true, true)
    if (menu.size > 0) {
        menu.findItem(tab).isChecked = true
    }
    return true
}
