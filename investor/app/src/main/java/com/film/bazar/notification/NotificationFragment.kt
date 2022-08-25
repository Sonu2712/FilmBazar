package com.film.bazar.notification

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.coreui.helper.LinearLayoutSpaceDecorator
import com.film.bazar.databinding.FragmentNotificationNewBinding
import com.film.bazar.domain.drawermenu.notification.NotificationData
import com.film.bazar.notification.item.NotificationItem
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.groupiex.section.DataManagerSection
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import javax.inject.Inject

class NotificationFragment : MOSLCommonFragment() , NotificationView{
    lateinit var binding : FragmentNotificationNewBinding

    @Inject
    lateinit var presenter: NotificationPresenter
    lateinit var section: DataManagerSection
    lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    override fun getLayout(): Int {
        return R.layout.fragment_notification_new
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationNewBinding.bind(view)
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    private fun setupRecyclerView(){
        section = DataManagerSection(onRetryClick)
        groupAdapter = GroupAdapter()
        mLayoutManager = LinearLayoutManager(context)
        binding.rvNotification.apply {
            adapter = groupAdapter.apply { add(section) }
            layoutManager = mLayoutManager
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            addItemDecoration(
                LinearLayoutSpaceDecorator(resources.getDimension(com.film.bazar.home_ui.R.dimen.margin_medium).toInt())
            )
        }
    }

    override fun render(uiModel: UiModel<List<NotificationData>>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            section.showError(it)
        }
        uiModel.onSuccess {
            val data = it.map { NotificationItem(it) }
            section.update(data)
        }
    }

    override fun isDataEmpty(): Boolean {
        return section.isDataEmpty
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }

}