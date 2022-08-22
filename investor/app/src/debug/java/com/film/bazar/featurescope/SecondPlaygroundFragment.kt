package com.film.bazar.featurescope

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.Fragment2Binding

class SecondPlaygroundFragment : MOSLCommonFragment() {
    lateinit var binding: Fragment2Binding

    override fun getLayout(): Int {
        return R.layout.fragment2
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = Fragment2Binding.bind(view)

        binding.btnBuyAction.setOnClickListener {
        }
    }
}
