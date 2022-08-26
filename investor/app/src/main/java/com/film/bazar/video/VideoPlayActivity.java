package com.film.bazar.video;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.film.bazar.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    public static final String ARG_VIDEO_ID = "videoId";
    static final String YOUTUBE_API_KEY = "YOUR API KEY";
    private static final int RECOVERY_REQUEST = 1;
    String videoUrl;
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videoUrl = bundle.getString(ARG_VIDEO_ID);
        }

        youTubeView = findViewById(R.id.iv_youtube_view);
        youTubeView.initialize(YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.setFullscreen(true);
            player.loadVideo(videoUrl);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.app_player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            youTubeView.initialize(YOUTUBE_API_KEY, this);
        }
    }
}
