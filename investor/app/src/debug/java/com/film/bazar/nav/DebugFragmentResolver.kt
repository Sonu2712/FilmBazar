package com.film.bazar.nav

import androidx.fragment.app.Fragment
import com.film.bazar.menu.MenuGetter
import com.film.bazar.coreui.navigatorlib.FragmentResolver

object DebugFragmentResolver : FragmentResolver {
    override fun resolve(pageId: String): Fragment? {
        return  MenuGetter.getFragment(pageId)
    }
}
