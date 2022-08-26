package com.film.bazar.home_ui.detail.manager.castcrew

import android.view.View
import androidx.core.view.isVisible
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.home_domain.CastCrewDetail
import com.film.bazar.home_ui.HomeUiEvent
import com.film.bazar.home_ui.R
import com.film.bazar.home_ui.databinding.ItemCastCrewHeaderTitleBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class CastCrewHeaderTitleItem(
    val uiEvent: PublishSubject<HomeUiEvent>
) : ViewBindingItem<ItemCastCrewHeaderTitleBinding>(), View.OnClickListener{
    var binding : ItemCastCrewHeaderTitleBinding? = null
    override fun getLayout(): Int {
        return R.layout.item_cast_crew_header_title
    }

    override fun initBinding(itemView: View): ItemCastCrewHeaderTitleBinding {
        return ItemCastCrewHeaderTitleBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemCastCrewHeaderTitleBinding, position: Int) {
        this.binding=viewBinding
        binding?.root?.setOnClickListener(this)
    }

    fun renderCastDetail(data : CastCrewDetail){
        binding?.let {
            it.group.isVisible = true
            it.tvDirectorName.text = data.directorName
        }
    }

    override fun onClick(v: View?) {
        uiEvent.onNext(HomeUiEvent.OpenCastCrew(1))
    }

}