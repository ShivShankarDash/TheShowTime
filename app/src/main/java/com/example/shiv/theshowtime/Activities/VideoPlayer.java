package com.example.shiv.theshowtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shiv.theshowtime.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    final String YOUTUBE_API_KEY = "AIzaSyAgeR5D7phdxkN8SYUKQIf8aTO1iz-kGao";
    String videoKey;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        youTubePlayerView = findViewById(R.id.youtubeview);


        Intent receivedVideoIntent = getIntent();
        videoKey = receivedVideoIntent.getStringExtra("VideoKey");
        youTubePlayerView = findViewById(R.id.youtubeview);
        youTubePlayerView.initialize(YOUTUBE_API_KEY, this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if (!b) {

            youTubePlayer.cueVideo(videoKey);
            youTubePlayer.setFullscreen(true);

        }


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }


    }
}
