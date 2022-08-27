package com.film.bazar.profile.helpsupport.questionanswer

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentPaymentRefundBinding
import com.film.bazar.domain.drawermenu.profile.AnswerValue
import com.film.bazar.domain.drawermenu.profile.AnswerYesNo
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.groupiex.section.DataManagerSection
import com.jakewharton.rxbinding4.view.clicks
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class QuestionAnswerFragment : MOSLCommonFragment(), PaymentRefundView {
    lateinit var binding: FragmentPaymentRefundBinding

    lateinit var section: DataManagerSection
    var questionData: List<AnswerValue> = emptyList()

    @Inject
    lateinit var presenter: PaymentRefundPresenter

    override fun getLayout(): Int {
        return R.layout.fragment_payment_refund
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentRefundBinding.bind(view)
        val title = arguments?.getString(PaymentRefundView.ARG_QUESTION_TITLE) ?: ""
        setTitle(title)
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    fun setupRecyclerView() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        binding.rvPaymentRefund.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = groupAdapter
        }
        section = DataManagerSection(onRetryClick)
        section.retrySubject = dataActionSubject
        groupAdapter.add(section)
    }

    override fun render(uiModel: UiModel<List<AnswerValue>>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            section.showError(it)
        }

        uiModel.onSuccess { data ->
            questionData = data
            data.map {
                val expandableGroup = ExpandableGroup(QuestionExpandableItem(it.key))
                it.value.map { data1 ->
                    expandableGroup.add(QuestionAnswerItem(data1))
                }
                expandableGroup.add(AnswerYesNoItem(it))
                section.add(expandableGroup)
            }
        }
    }

    override fun showMsg(uiModel: UiModel<String>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }
        uiModel.onSuccess {
            toast(it)
        }
    }

    override fun onSubmitClicked(): Observable<Unit> {
        return binding.btnSubmit.clicks()
    }

    override fun isNoSelected(): Boolean {
        return questionData.any {
            it.selected == AnswerYesNo.NO
        }
    }

    override fun getInitialState(): ContainerState {
        return ContainerState.detailPage()
    }

    override fun isDataEmpty(): Boolean {
        return section.isDataEmpty
    }
}