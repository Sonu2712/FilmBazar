package com.film.login_ui

import androidx.fragment.app.Fragment
import com.film.bazar.coreui.navigatorlib.FragmentResolver

object LoginFragmentResolver : FragmentResolver {
    override fun resolve(pageId: String): Fragment? {
        return LoginFragmentGetter.getFragment(pageId)
    }
}
