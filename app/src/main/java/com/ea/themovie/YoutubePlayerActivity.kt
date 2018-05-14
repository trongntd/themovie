package com.ea.themovie

import android.content.Intent
import android.os.Bundle
import com.ea.themovie.util.Constants
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_youtube_player.*
import android.widget.Toast



class YoutubePlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    var videoId : String? = null
    private val RECOVERY_DIALOG_REQUEST = 1

    companion object {
        @JvmStatic val EX_VIDEO_ID = "video_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player)

        videoId = intent?.getStringExtra(EX_VIDEO_ID)
        youTubePlayerView.initialize(Constants.GOOGLE_API_KEY, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if (!wasRestored) {
            player?.cueVideo(videoId)
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            val errorMessage = errorReason.toString()
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            youTubePlayerView.initialize(Constants.GOOGLE_API_KEY, this)
        }
    }


}