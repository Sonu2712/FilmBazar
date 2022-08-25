package com.film.bazar.home_ui.sortfilter

import android.view.View
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.FragmentSortFilterBottomsheetBinding

class SortFilterBottomSheetFragment : DaggerBaseBottomSheetFragment() {
    lateinit var binding : FragmentSortFilterBottomsheetBinding
    override fun getLayout(): Int {
        return R.layout.fragment_sort_filter_bottomsheet
    }

    override fun setupView(view: View) {
        binding = FragmentSortFilterBottomsheetBinding.bind(view)
    }
}