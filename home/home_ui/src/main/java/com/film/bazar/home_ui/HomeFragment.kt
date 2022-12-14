package com.film.bazar.home_ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.coreui.helper.LinearLayoutSpaceDecorator
import com.film.bazar.coreui.navigatorlib.AppTitle
import com.film.bazar.home_domain.MovieData
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_domain.MovieTab
import com.film.bazar.home_ui.databinding.FragmentHomeBinding
import com.film.bazar.home_ui.moviebanner.MovieBannerManager
import com.film.bazar.home_ui.movieinfo.MovieInfoManager
import com.film.bazar.home_ui.movietab.MovieTabManager
import com.film.bazar.home_ui.sortfilter.SortFilterBottomSheetFragment
import com.film.commons.data.UiModel
import com.film.commons.data.onFailure
import com.film.commons.data.onSuccess
import com.film.commons.rx.ofType
import com.film.groupiex.section.DataManagerSection
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject


class HomeFragment : MOSLCommonFragment(), HomeView {
    lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter
    val uiEvent = PublishSubject.create<HomeUiEvent>()
    lateinit var section: DataManagerSection
    lateinit var dataSection: DataManagerSection
    lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    lateinit var bannerManager: MovieBannerManager
    lateinit var tabManager: MovieTabManager
    lateinit var infoManager: MovieInfoManager
    var grouplist = mutableListOf<Group>()

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
        setTitle(AppTitle.withSubTitle(title = R.string.page_title_home, subTitle = true))
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    private fun setupRecyclerView() {
        bannerManager = MovieBannerManager()
        tabManager = MovieTabManager(uiEvent)
        infoManager = MovieInfoManager(uiEvent)

        section = DataManagerSection(onRetryClick)
        dataSection = DataManagerSection(onRetryClick)
        groupAdapter = GroupAdapter()
        mLayoutManager = LinearLayoutManager(context)
        binding.rvHome.apply {
            adapter = groupAdapter.apply {
                add(section)
                add(dataSection)
            }
            layoutManager = mLayoutManager
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            addItemDecoration(
                LinearLayoutSpaceDecorator(resources.getDimension(R.dimen.margin_medium).toInt())
            )
        }
    }

    override fun renderWelcome(uiModel: UiModel<MovieData>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            section.clearContent()
            section.showError(it)
        }
        uiModel.onSuccess {
            section.removeAll(grouplist)
            if (!it.banner.isNullOrEmpty()) {
                val bannerGroup = bannerManager.render(it.banner)
                section.add(bannerGroup)
                grouplist.add(bannerGroup)
            }
            if (!it.tab.isNullOrEmpty()) {
                val tabGroup = tabManager.render(it.tab)
                section.add(tabGroup)
                grouplist.add(tabGroup)
            }

            render(it.info)

        }
    }

    fun render(movieInfo: List<MovieInfo>) {
        dataSection.clearContent()
        val infoGroup = infoManager.render(movieInfo)
        dataSection.add(infoGroup)
        grouplist.add(infoGroup)
    }

    override fun renderMovie(uiModel: UiModel<List<MovieInfo>>) {
        dataSection.toggleProgress(uiModel.inProgress)
        uiModel.onFailure {
            dataSection.showError(it)
        }

        uiModel.onSuccess {
            render(it)
        }
    }

    override fun onMovieTabClicked(): Observable<HomeUiEvent.MovieTabChanged> {
        return uiEvent.ofType()
    }

    override fun isDataEmpty(): Boolean {
        return section.isDataEmpty
    }

    override fun onFilterClicked(): Observable<HomeUiEvent.OpenSortFilterBottomSheet> {
        return uiEvent.ofType()
    }

    override fun showSortFilterBottomSheet(tab: MovieTab) {
        SortFilterBottomSheetFragment()
            .show(childFragmentManager, "SortFilterBottomSheetFragment")
    }

    override fun sortFilterApplied(): Observable<HomeUiEvent.FilterApplied> {
        return uiEvent.ofType()
    }

    override fun onNavigationEvent(): Observable<HomeUiEvent.NavigationEvent> {
        return uiEvent.ofType()
    }

    override fun getSelectedMovieTab(): MovieTab {
        return tabManager.currentTab
    }

    override fun onMovieInfoClicked(): Observable<HomeUiEvent.MovieDetail> {
        return uiEvent.ofType()
    }

    override fun getOptionsMenu(): Int {
        return R.menu.home_menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_notification -> {
                uiEvent.onNext(HomeNavEvent.NotificationEvent)
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