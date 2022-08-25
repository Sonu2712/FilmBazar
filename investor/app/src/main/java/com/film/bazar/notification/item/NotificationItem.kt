package com.film.bazar.notification.item

import android.view.View
import androidx.core.content.ContextCompat
import coil.api.load
import com.film.bazar.R
import com.film.bazar.coreui.groupie.ViewBindingItem
import com.film.bazar.databinding.ItemNotificationBinding
import com.film.bazar.domain.drawermenu.notification.NotificationData

class NotificationItem(
    val data : NotificationData
) : ViewBindingItem<ItemNotificationBinding>(){
    override fun getLayout(): Int {
        return R.layout.item_notification
    }

    override fun initBinding(itemView: View): ItemNotificationBinding {
        return ItemNotificationBinding.bind(itemView)
    }

    override fun bind(viewBinding: ItemNotificationBinding, position: Int) {
        viewBinding.apply {
            ivThumbnail
                .load(data.imageUrl)
            txtTitle.text = data.title
            txtSubtitle.text = data.subTitle
            tvDays.text = "${data.daysTime} mins ago"
        }
    }
}