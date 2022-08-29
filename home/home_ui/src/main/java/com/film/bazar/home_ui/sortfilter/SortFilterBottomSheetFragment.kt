package com.film.bazar.home_ui.sortfilter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.home_domain.*
import com.film.bazar.home_ui.HomeFragment
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.FragmentSortFilterBottomsheetBinding
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class SortFilterBottomSheetFragment : DaggerBaseBottomSheetFragment(), SortFilterView {
    lateinit var binding: FragmentSortFilterBottomsheetBinding

    @Inject
    lateinit var presenter: SortFilterPresenter
    lateinit var uiEventSubject: PublishSubject<HomeUiEvent>
    lateinit var sortFilterInput: LocalSortFilterInput

    override fun onAttach(context: Context) {
        uiEventSubject = (parentFragment as? HomeFragment)?.uiEvent
            ?: PublishSubject.create()
        super.onAttach(context)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_sort_filter_bottomsheet
    }

    override fun setupView(view: View) {
        binding = FragmentSortFilterBottomsheetBinding.bind(view)
        presenter.start()
        setupRecyclerView()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    private fun setupRecyclerView() {
        sortFilterInput = LocalSortFilterInput()

        binding.slInvestment.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("IND")
            format.format(value.toDouble())
        }
        binding.slInvestment.addOnChangeListener { rangeSlider, value, fromUser ->
            val values = rangeSlider.values
            sortFilterInput.saveAmountRange(values[0].toDouble(), values[1].toDouble())
        }

        binding.rvSort.setOnCheckedChangeListener { group, checkedId ->
            sortFilterInput.setLocalSort(MovieSortFilter.getInstance(checkedId))
        }
    }

    override fun render(uiModel: UiModel<MovieSort>) {
        uiModel.onSuccess { sort ->
            binding.slInvestment.apply {
                try {
                    setValues(
                        sort.selectedFrom?.toFloat() ?: 0.0f,
                        sort.selectedTo?.toFloat() ?: 1.0f
                    )
                    valueTo = sort.valueTo.toFloat()
                    valueFrom = sort.valueFrom.toFloat()
                } catch (exception: IllegalStateException) {
                    Timber.d("FilmD : (valueFrom,SelectedFrom) : (${sort.valueFrom}, ${sort.selectedFrom}), (valueTo, selectedTo) : (${sort.valueTo}, ${sort.selectedTo}), Exception $exception")
                }

            }
            sort.sort.map {
                val radioButton = getRadioSortButton(it, sort.selectedSort)
                binding.rvSort.addView(radioButton)
            }
        }
    }

    private fun getRadioSortButton(
        data: MovieSortFilter,
        selectedFilter: MovieSortFilter
    ): RadioButton {
        val rb = RadioButton(requireContext())
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        rb.layoutParams = params
        params.setMargins(18, 12, 12, 12)
        rb.setPadding(12, 12, 12, 12)
        rb.setButtonDrawable(R.color.transparent)
        rb.background = ContextCompat.getDrawable(requireContext(), R.drawable.sort_radio_bg)
        rb.id = data.id
        rb.tag = data.label
        rb.text = data.label
        rb.isChecked = data.id == selectedFilter.id
        return rb
    }

    override fun overrideSortFilter(sortKeyValue: MovieSortFilter) {
        sortFilterInput.setLocalSort(sortKeyValue)
    }

    override fun onApplyClicked(): Observable<Unit> {
        return binding.btnApply.clicks()
    }

    override fun onResetClicked(): Observable<Unit> {
        return binding.tvReset.clicks()
    }

    override fun getFilter(): MovieFilter {
        return MovieFilter(
            startAmount = sortFilterInput.valueFrom1,
            endAmount = sortFilterInput.valueTo1,
            filterType = sortFilterInput.selectedSort
        )
    }

    override fun dismissFilter(uiModel: UiModel<MovieFilter>) {
        uiModel.onSuccess {
            dismiss()
            uiEventSubject.onNext(HomeUiEvent.FilterApplied(it))
        }
    }

    override fun onDestroy() {
        presenter.stop()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiEventSubject.onNext(HomeUiEvent.ResetSortFilter)
    }
}