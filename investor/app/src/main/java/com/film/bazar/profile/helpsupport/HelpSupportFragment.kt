package com.film.bazar.profile.helpsupport

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.core.events.DataAction
import com.film.bazar.R
import com.film.bazar.coreui.core.ContainerState
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.databinding.FragmentHelpSupportBinding
import com.film.bazar.domain.drawermenu.profile.HelpSupportQuestions
import com.film.bazar.home_ui.HomeNavEvent
import com.film.bazar.home_ui.onNext
import com.film.bazar.profile.ProfileUiEvent
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.film.groupiex.section.DataManagerSection
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        setHasOptionsMenu(true)
        setTitle("Help & Support")
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

    override fun onCallClicked(): Observable<ProfileUiEvent.CallUS> {
        return uiEvent.ofType()
    }

    override fun callToTradePopup() {
        val phoneNumbers = arrayOf("22222222", "3333333")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.app_label_call)
            .setItems(arrayOf("02240548810", "02262701776")) { _, which ->
                requireContext().initiateCall(phoneNumbers[which])
            }
            .setNegativeButton(R.string.button_label_cancel, null)
            .show()
    }

    override fun onChatClicked(): Observable<ProfileUiEvent.ChatWithUS> {
        return uiEvent.ofType()
    }

    override fun onWritUsClicked(): Observable<Unit> {
        return binding.tvWriteUs.clicks()
    }

    override fun getOptionsMenu(): Int {
        return R.menu.help_support_menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_call -> {
                uiEvent.onNext(ProfileUiEvent.CallUS)
                true
            }
            R.id.nav_chat -> {
                uiEvent.onNext(ProfileUiEvent.ChatWithUS)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun isDataEmpty(): Boolean {
        return section.isDataEmpty
    }

    override fun showMessage(msg: String) {
        toast(msg)
    }

    fun Context.initiateCall(mobileNo: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$mobileNo"))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}