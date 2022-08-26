package com.film.bazar.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

interface VideoNavigator {
    fun playVideo(videoId: String)
}

class VideoNavigatorImpl @Inject constructor(
    val activity: AppCompatActivity
) : VideoNavigator {

    override fun playVideo(videoId: String) {
        val args = Bundle().apply {
            putString(VideoPlayActivity.ARG_VIDEO_ID, videoId)
        }
        playVideo(args)
    }

    fun playVideo(args: Bundle) {
        val intent = Intent(activity, VideoPlayActivity::class.java)
        intent.putExtras(args)
        activity.startActivity(intent)
    }
}
