package com.film.bazar.profile.helpsupport.writeus.confirmation

import android.view.View
import com.film.bazar.R
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.databinding.FragmentConfirmationWriteUsBinding
import com.film.bazar.databinding.FragmentWriteToUsBinding
import com.film.bazar.profile.helpsupport.writeus.WriteToUsPresenter
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ConfirmationBottomSheetFragment : DaggerBaseBottomSheetFragment(), ConfirmationBottomSheetView {
    lateinit var binding: FragmentConfirmationWriteUsBinding

    @Inject
    lateinit var presenetr : ConfirmationBottomSheetPresenter
    override fun getLayout(): Int {
        return R.layout.fragment_confirmation_write_us
    }

    override fun setupView(view: View) {
        binding = FragmentConfirmationWriteUsBinding.bind(view)
        presenetr.start()
        binding.tvTicketNo.text = this.tag
    }

    override fun onSubmitClicked(): Observable<Unit> {
        return binding.btnSubmit.clicks()
    }

    override fun onCloseClicked(): Observable<Unit> {
        return binding.ivClose.clicks()
    }

    override fun dismissBottomSheet() {
        dismiss()
    }
}