package com.film.bazar.featurescope

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.film.bazar.R
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.coreui.helper.adapter.SpinnerAdapterFactory.getSpinnerDefaultAdapter
import com.film.bazar.databinding.FragmentFeatureMainBinding
import timber.log.Timber
import javax.inject.Inject

class FirstPlaygroundFragment : MOSLCommonFragment() {
    lateinit var binding: FragmentFeatureMainBinding
    val COUNTRIES = listOf("Item 1", "Item 2", "Item 3", "Item 4")

    override fun getLayout(): Int {
        return R.layout.fragment_feature_main
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeatureMainBinding.bind(view)
        setHasOptionsMenu(true)
        //binding.filledExposedDropdown.setAdapter(adapter)
        //binding.outlineDropdown.setAdapter(adapter)
        binding.spinner.adapter = getSpinnerDefaultAdapter(requireContext(), COUNTRIES)
    }

    override fun getOptionsMenu(): Int {
        return R.menu.investor_toolbar_menus
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Timber.i(item.itemId.toString() + "")
                return super.onOptionsItemSelected(item)
            }
            R.id.nav_search -> {
                Timber.i(item.itemId.toString() + "")
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
