package com.film.bazar.menu

import androidx.fragment.app.Fragment
import com.film.bazar.coreui.navigatorlib.FragmentResolver

object AppFragmentResolver : FragmentResolver {
    override fun resolve(pageId: String): Fragment? {
        return MenuGetter.getFragment(pageId)
    }
}
