package com.film.bazar.profile.helpsupport.writeus

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentWelcomeBinding

class WriteUsFragment : MOSLCommonFragment() {
    lateinit var binding : FragmentWelcomeBinding

    override fun getLayout(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)
        setTitle("Tell us about the issue")
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }
}