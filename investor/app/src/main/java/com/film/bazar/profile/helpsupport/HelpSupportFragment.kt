package com.film.bazar.profile.helpsupport

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentHelpSupportBinding
import com.film.bazar.domain.drawermenu.profile.HelpSupportQuestions
import com.film.bazar.profile.ProfileUiEvent
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.film.groupiex.section.DataManagerSection
import com.jakewharton.rxbinding4.view.clicks
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class HelpSupportFragment : MOSLCommonFragment(), HelpSupportView {
    lateinit var binding: FragmentHelpSupportBinding

    @Inject
    lateinit var presenter: HelpSupportPresenter
    lateinit var section: DataManagerSection
    val uiEvent = PublishSubject.create<ProfileUiEvent>()

    override fun getLayout(): Int {
        return R.layout.fragment_help_support
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpSupportBinding.bind(view)
        setTitle("Help and Support")
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }

    fun setupRecyclerView() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvSupport.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = groupAdapter
        }
        section = DataManagerSection(onRetryClick)
        section.retrySubject = dataActionSubject
        groupAdapter.add(section)
    }

    override fun render(uiModel: UiModel<List<HelpSupportQuestions>>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            section.showError(it)
        }

        uiModel.onSuccess { data ->
            val items = data.map { HelpSupportItem(it, uiEvent) }
            section.update(items)
        }
    }

    override fun onItemClicked(): Observable<ProfileUiEvent.OnItemClicked> {
        return uiEvent.ofType()
    }

    override fun onWritUsClicked(): Observable<Unit> {
        return binding.tvWriteUs.clicks()
    }

    override fun isDataEmpty(): Boolean {
        return section.isDataEmpty
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }
}