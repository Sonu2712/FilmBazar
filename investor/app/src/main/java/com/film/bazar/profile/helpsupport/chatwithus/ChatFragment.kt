package com.film.bazar.profile.helpsupport.chatwithus

import android.os.Bundle
import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentWelcomeBinding

class ChatFragment : MOSLCommonFragment() {
    lateinit var binding: FragmentWelcomeBinding
    override fun getLayout(): Int {
        return R.layout.fragment_welcome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWelcomeBinding.bind(view)
        setTitle("Chat with us")
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.showBackNavigation()
    }
}