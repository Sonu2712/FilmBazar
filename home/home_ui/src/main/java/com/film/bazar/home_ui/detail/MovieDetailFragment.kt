package com.film.bazar.home_ui.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.film.app.core.events.DataAction
import com.film.bazar.coreui.core.MOSLCommonFragment
import com.film.bazar.coreui.helper.GridLayoutSpaceDecorator
import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_domain.MovieDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.FragmentMovieDetailNewBinding
import com.film.bazar.home_ui.detail.manager.bannerheader.MovieDetailBannerManager
import com.film.bazar.home_ui.detail.manager.castcrew.CastCrewManager
import com.film.bazar.home_ui.detail.manager.fundingprogress.FundingProgressManager
import com.film.bazar.home_ui.detail.manager.invtinfo.InvestmentInfoManager
import com.film.bazar.home_ui.detail.manager.movievideo.MovieVideoManager
import com.film.bazar.home_ui.detail.manager.titlesubtitlepair.TitleSubtitlePairManager
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

class MovieDetailFragment : MOSLCommonFragment(), MovieDetailView {
    lateinit var binding: FragmentMovieDetailNewBinding

    @Inject
    lateinit var presenter: MovieDetailPresenter
    lateinit var section: DataManagerSection
    private val uiEvent = PublishSubject.create<HomeUiEvent>()
    protected lateinit var gridLayoutManager: GridLayoutManager
    internal lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    lateinit var bannerHeaderManager : MovieDetailBannerManager
    lateinit var fundingProgressManager: FundingProgressManager
    lateinit var investmentInfoManager: InvestmentInfoManager
    lateinit var titleSubtitleManager : TitleSubtitlePairManager
    lateinit var castCrewManager: CastCrewManager
    lateinit var videoManager : MovieVideoManager

    var grouplist = mutableListOf<Group>()

    override fun getLayout(): Int {
        return R.layout.fragment_movie_detail_new
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailNewBinding.bind(view)
        setupRecyclerView()
        presenter.start()
        dataActionSubject.onNext(DataAction.Fetch)
    }

    private fun setupRecyclerView() {
        bannerHeaderManager = MovieDetailBannerManager()
        fundingProgressManager = FundingProgressManager()
        investmentInfoManager = InvestmentInfoManager()
        titleSubtitleManager = TitleSubtitlePairManager()
        castCrewManager = CastCrewManager(uiEvent)
        videoManager = MovieVideoManager(uiEvent = uiEvent )

        section = DataManagerSection(onRetryClick)
        groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(section)
            spanCount = 2
        }
        gridLayoutManager = GridLayoutManager(requireContext(), groupAdapter.spanCount).apply {
            spanSizeLookup = groupAdapter.spanSizeLookup
        }
        binding.rvMovieDetail.apply {
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            adapter = groupAdapter
            layoutManager = gridLayoutManager
            addItemDecoration(
                GridLayoutSpaceDecorator(
                    resources.getDimension(R.dimen.margin_small).toInt()
                )
            )
        }
    }

    override fun render(uiModel: UiModel<MovieDetail>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            section.clearContent()
            section.showError(it)
        }
        uiModel.onSuccess {
            section.removeAll(grouplist)
            val bannerGroup = bannerHeaderManager.render(it.bannerInfo)
            section.add(bannerGroup)
            grouplist.add(bannerGroup)

            val fundingGroup = fundingProgressManager.render(it.movieFund)
            section.add(fundingGroup)
            grouplist.add(fundingGroup)

            val invtGroup = investmentInfoManager.render(it.invtInfo)
            section.add(invtGroup)
            grouplist.add(invtGroup)

            val titleSubtitleGroup = titleSubtitleManager.render(it.titleSubTitle)
            section.add(titleSubtitleGroup)
            grouplist.add(titleSubtitleGroup)

            val castCrewGroup = castCrewManager.render(it.castCrewDetail)
            section.add(castCrewGroup)
            grouplist.add(castCrewGroup)

            val videoGroup = videoManager.render(it.videoInfo)
            section.add(videoGroup)
            grouplist.add(videoGroup)

        }
    }

    override fun onNavigationEvent(): Observable<HomeUiEvent.NavigationEvent> {
        return uiEvent.ofType()
    }

    override fun openCastCrew(): Observable<HomeUiEvent.OpenCastCrew> {
        return uiEvent.ofType()
    }

    override fun renderCarsCrew(uiModel: UiModel<CastCrewDetail>) {
        toggleProgressBar(uiModel.inProgress)
        uiModel.onFailure {
            showOnFailurePopup(it)
        }

        uiModel.onSuccess {
            castCrewManager.renderCastCrewDetail(it)
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