package com.film.bazar.dashboard

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.bazar.R
import com.jakewharton.rxbinding4.widget.textChanges
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentDashboardBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import com.xwray.groupie.databinding.BindableItem
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DashboardFragment : MOSLCommonFragment(), DashboardContract.View {
    @Inject
    lateinit var presenter: DashboardPresenter
    lateinit var binding: FragmentDashboardBinding
    lateinit var section: Section

    override fun getLayout(): Int {
        return R.layout.fragment_dashboard
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        setupRecyclerView()
        presenter.start()
    }

    fun setupRecyclerView() {
        val context = requireContext()
        val dashboardAdapter = GroupAdapter<GroupieViewHolder>()
        dashboardAdapter.setOnItemClickListener { item, _ ->
        }
        section = Section()
        dashboardAdapter.add(section)
        binding.rvDashboard.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
            adapter = dashboardAdapter
        }
    }

    private fun navigateDashboardFragment(pageId: String, bundle: Bundle = Bundle.EMPTY) {
        screenNavigator.openPage(pageId, bundle, true)
    }

    override fun onSearchChanged(): Observable<CharSequence> {
        return binding.editSearch.textChanges()
    }

    override fun renderPage(data: List<DashboardHeader>) {
        val items = mutableListOf<BindableItem<*>>()
        section.update(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
        binding.rvDashboard.adapter = null
    }
}
