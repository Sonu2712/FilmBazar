package com.film.bazar.home_ui.movieinfo

import android.view.View
import androidx.core.view.isVisible
import coil.api.load
import com.film.app.core.utils.toFormattedValue
import com.film.app.core.utils.toRoundedRupees
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieInfo
import com.film.bazar.home_domain.MovieTab
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemMovieBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieItem(
    val data : MovieInfo,
    val uiEvent: PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemMovieBinding>(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.item_movie
    }

    override fun initBinding(itemView: View): ItemMovieBinding {
        return ItemMovieBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemMovieBinding, position: Int) {
        val isOngoing = data.tab == MovieTab.OngoingProject
        viewBinding.apply {
            imgScreen
                .load(data.imgUrl)
            tvTitle.text = data.title
            tvSubTitleValue.text = data.directorName
            tvDaysValue.text = data.noOfDaysLeft.toString()
            tvPeopleInvestedValue.text = data.noOfPeopleInvt.toFormattedValue()
            tvFoundedPer.text = "${data.perFoundProgress}%"
            pbGrowth.progress = data.perFoundProgress
            tvTargetValue.text = data.targetAmount.toRoundedRupees()
            tvTargetGoal.text = "of ${data.targetAmount.toRoundedRupees()} goals"
            btnBuy.text = data.orderAction
            groupDays.isVisible = isOngoing
            guideLine.setGuidelinePercent(if (isOngoing) .35f else 0.0f)
            guideLineMiddle.setGuidelinePercent(if (isOngoing) .65f else 0.35f)
        }
        viewBinding.root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        uiEvent.onNext(HomeUiEvent.MovieDetail(1, ""))
    }
}