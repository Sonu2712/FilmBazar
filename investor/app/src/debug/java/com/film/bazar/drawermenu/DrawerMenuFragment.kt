package com.film.bazar.drawermenu

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.jakewharton.rxbinding4.widget.itemSelections
import com.film.app.core.rx.applyUiModel
import com.film.bazar.appusercore.model.UserType
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentDrawerMenuBinding
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DrawerMenuFragment : MOSLCommonFragment() {
    lateinit var binding: FragmentDrawerMenuBinding

    @Inject
    lateinit var repository : MenuDataSourceRepository

    override fun getLayout(): Int {
        return R.layout.fragment_drawer_menu
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDrawerMenuBinding.bind(view)

        disposable.add(onUserTypeChanged().map { UserType.GUEST }
            .switchMap {
                repository.getMenuItems()
                    .compose(applyUiModel())
            }.subscribe { binding.drawerMenu.render(it) })
    }

    fun onUserTypeChanged(): Observable<Int> {
        return binding.spinnerUserType.itemSelections()
    }
}
