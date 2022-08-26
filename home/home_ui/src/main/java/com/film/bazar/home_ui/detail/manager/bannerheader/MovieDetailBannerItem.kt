package com.film.bazar.home_ui.detail.manager.bannerheader

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import coil.api.load
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.MovieDetailBannerInfo
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemMovieBannerHeaderBinding
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import io.reactivex.rxjava3.subjects.PublishSubject

class MovieDetailBannerItem(
    val data : MovieDetailBannerInfo,
    val uiEvent: PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemMovieBannerHeaderBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_movie_banner_header
    }

    override fun initBinding(itemView: View): ItemMovieBannerHeaderBinding {
        return ItemMovieBannerHeaderBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemMovieBannerHeaderBinding, position: Int) {
        viewBinding.apply {
            imgScreen.load(data.bannerUrl)
            tvTitle.text = data.title
            llInvestmentAmount.removeAllViews()
            data.movieGenre.map {
                llInvestmentAmount.addView(createAmountChips(it, this.root.context))
            }
            tvHedingTitle.setOnClickListener {
                uiEvent.onNext(HomeUiEvent.GoBack)
            }
        }
    }
}

private fun createAmountChips(amount: String, context: Context): Chip {
    val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 0, 16, 0)
    }

    val chip = Chip(context)
    chip.apply {
        chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context, R.color.color_surface
            )
        )
        shapeAppearanceModel = ShapeAppearanceModel.Builder().setAllCornerSizes(8F).build()
        chipStrokeColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                context,
                R.color.action_text_color
            )
        )
        chipStrokeWidth = 1f
        tag = amount
        text = amount
        setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.action_text_color
                )
            )
        )
        layoutParams = params
    }
    return chip
}