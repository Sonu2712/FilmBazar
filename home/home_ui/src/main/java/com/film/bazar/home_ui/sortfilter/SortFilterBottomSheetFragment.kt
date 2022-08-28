package com.film.bazar.home_ui.sortfilter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.appcoreui.bottomsheet.DaggerBaseBottomSheetFragment
import com.film.bazar.home_domain.MovieFilter
import com.film.bazar.home_domain.MovieFilterType
import com.film.bazar.home_domain.MovieSort
import com.film.bazar.home_domain.MovieSortKeyValue
import com.film.bazar.home_ui.HomeFragment
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.FragmentSortFilterBottomsheetBinding
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.google.android.material.slider.RangeSlider
import com.jakewharton.rxbinding4.view.clicks
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
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
    lateinit var section: Section
    val itemList: MutableList<MovieSortItem> = mutableListOf()
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
        section = Section()
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        groupAdapter.add(section)

        binding.rvSort.apply {
            adapter = groupAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
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
    }

    override fun render(uiModel: UiModel<MovieSort>) {
        uiModel.onSuccess {
            val sortFilter = sortFilterInput
            val fromValue = if(sortFilter.valueFrom1 == 0.0) it.valueFrom else sortFilter.valueFrom1
            val toValue = if(sortFilter.valueTo1 == 0.0) it.valueTo else sortFilter.valueTo1
            binding.slInvestment.apply {
                setValues(fromValue.toFloat(), toValue.toFloat())
                valueTo = toValue.toFloat()
                valueFrom = fromValue.toFloat()
            }
            val items = it.sort.map { MovieSortItem(it, uiEventSubject) }
            itemList.clear()
            itemList.addAll(items)
            section.update(itemList)
        }
    }

    override fun onItemClicked(): Observable<HomeUiEvent.SortItemClicked> {
        return uiEventSubject.ofType()
    }

    override fun overrideSortFilter(sortKeyValue: MovieSortKeyValue) {
        sortFilterInput.setLocalSort(sortKeyValue.filterType)
    }

    override fun onApplyClicked(): Observable<Unit> {
        return binding.btnApply.clicks()
    }

    override fun getFilter(): MovieSort {
        return MovieSort(
            valueFrom = sortFilterInput.valueFrom1,
            valueTo = sortFilterInput.valueTo1,
            selectedSort = sortFilterInput.selectedSort
        )
    }

    override fun dismissFilter(uiModel: UiModel<MovieSort>) {
        uiModel.onSuccess {
            dismiss()
            uiEventSubject.onNext(HomeUiEvent.FilterApplied(it))
        }
    }

    override fun onDestroy() {
        presenter.stop()
        binding.rvSort.adapter = null
        super.onDestroy()
    }
}