package com.film.bazar.home_ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.core.InvestorBaseFragment
import com.film.commons.data.UiModel
import com.film.commons.data.onSuccess
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_ui.databinding.FragmentHomeBinding
import com.film.commons.data.onFailure
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import javax.inject.Inject


class HomeFragment : InvestorBaseFragment() , HomeView{
    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter
    lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>


    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun getPageName(): String {
        return "Home"
    }

    override fun getEventName(): String {
        return "home_page"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.bind(view)
        setTitle(R.string.page_title_home)
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    override fun renderWelcome(uiModel: UiModel<List<MovieInfo>>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {

        }
        uiModel.onSuccess {

        }
    }

    override fun isDataEmpty(): Boolean {
        return true
    }

    override fun getOptionsMenu(): Int {
        return R.menu.home_menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_notification -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }
}