package com.app.addviu.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import com.app.addviu.R
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.presenter.VideoPlayerPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.fragments.VideoDetailsFragment
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.video_player_layout.*
import vimeoextractor.OnVimeoExtractionListener
import vimeoextractor.VimeoExtractor
import vimeoextractor.VimeoVideo

@SuppressLint("SourceLockedOrientationActivity")
class VideoPlayerScreen : BaseActivity(), View.OnClickListener {

    val adUrl = ("https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/"
            + "single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast"
            + "&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct"
            + "%3Dskippablelinear&correlator=")
    var videoUrl = ""
    private var isLandscape = false
    private var player: SimpleExoPlayer? = null
    private var adsLoader: ImaAdsLoader? = null
    private var mediaSourceFactory: ProgressiveMediaSource.Factory? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var videoPlayerPresenter: VideoPlayerPresenter? = null
    private var videoUid = ""
    var relatedVideo: RelatedVideo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_player_layout)
        videoUid = intent.getStringExtra("uid") ?: ""
        videoPlayerPresenter = VideoPlayerPresenter(this)
        initializePlayer()
        addFramgent()
        setOnClickListeners()

    }

    fun addFramgent() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("uid", videoUid)
        val videoDetailsFragment = VideoDetailsFragment()
        videoDetailsFragment.arguments = bundle
//        fragmentTransaction.replace(R.id.detailContainer, videoDetailsFragment, "detailsFragment")
        fragmentTransaction.commit()
    }

    fun setVideoPlayerVisible() {
        videoContainer.visibility = VISIBLE
    }

    fun setRelatedVideoSelected(homeData: HomeData) {
        videoUid = homeData.uid
        videoContainer.visibility = INVISIBLE
        releasePlayer()
        adsLoader?.release()
        initializePlayer()
        addFramgent()
    }

    fun vimeoExtraction(videoFilename: String) {
        VimeoExtractor.getInstance()
            .fetchVideoWithURL(videoFilename, null, object : OnVimeoExtractionListener {
                override fun onSuccess(video: VimeoVideo?) {
                    videoUrl = video?.streams?.get(video.streams.keys.toTypedArray()[0])!!
                    val mediaSource: MediaSource =
                        mediaSourceFactory?.createMediaSource(Uri.parse(videoUrl))!!
                    val adsMediaSource =
                        AdsMediaSource(
                            mediaSource,
                            dataSourceFactory,
                            adsLoader,
                            videoContainer
                        )
                    runOnUiThread {
                        Log.e("starting", "fhfud")
                        player?.prepare(mediaSource)
                    }
                }

                override fun onFailure(throwable: Throwable?) {
                    Util.showToast(
                        "Video is corrupted or unable to load.",
                        this@VideoPlayerScreen
                    )
                }
            })
    }


    private fun initializePlayer() {
        // Create a SimpleExoPlayer and set is as the player for content and ads.
        player = SimpleExoPlayer.Builder(this).build()

        // Create an AdsLoader with the ad tag url.
        adsLoader = ImaAdsLoader(this, Uri.parse(adUrl))

        videoContainer.player = player
        videoContainer.setShutterBackgroundColor(Color.TRANSPARENT)
        videoContainer.setKeepContentOnPlayerReset(true)
        adsLoader?.setPlayer(player)
        dataSourceFactory =
            DefaultDataSourceFactory(
                this,
                com.google.android.exoplayer2.util.Util.getUserAgent(
                    this,
                    getString(R.string.app_name)
                )
            )
        mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        if (requestAudioFocusForMyApp(this)) {
            player?.playWhenReady = true
        }
        // Create the MediaSource for the content you wish to play.
    }

    private fun setOnClickListeners() {
        fullScreenImg.setOnClickListener(this)
        backImage.setOnClickListener(this)
        onVideoTouchListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val orientation = resources.configuration.orientation
        isLandscape =
            orientation == Configuration.ORIENTATION_LANDSCAPE
        videoPlayerPresenter?.makeFullScreen(
            videoExampleLayout,
            videoContainer,
            window,
            isLandscape
        )
        changeFullscreenImage()
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
        }
    }

    override fun onBackPressed() {
        if (isLandscape) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            val intent = Intent()
            intent.putExtra("data", relatedVideo)
            setResult(Activity.RESULT_OK, intent)
            super.onBackPressed()
//            releaseAudioFocusForMyApp(this)
            overridePendingTransition(0, R.anim.top_to_bottom)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.backImage -> {
                onBackPressed()
            }
            R.id.fullScreenImg -> {
                requestedOrientation = if (isLandscape) {
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                } else {
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }
        }
    }

    private fun changeFullscreenImage() {
        if (isLandscape) {
            fullScreenImg.setImageResource(R.drawable.ic_fullscreen_exit_white_36dp)
        } else {
            fullScreenImg.setImageResource(R.drawable.ic_fullscreen_white_36dp)
        }
    }


    override fun onResume() {
        super.onResume()
//        player?.playWhenReady = true
        player?.playbackState
    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
        player?.playbackState
    }

    override fun onDestroy() {
        releasePlayer()
        adsLoader?.release()
        super.onDestroy()
    }

    private fun releasePlayer() {
        adsLoader?.setPlayer(null)
        videoContainer.player = null
        player?.release()
        player = null
    }


    private fun onVideoTouchListener() {
        videoContainer.setControllerVisibilityListener { visible ->
            if (visible == 0) {
                backImage.visibility = VISIBLE
                fullScreenImg.visibility = VISIBLE
                videoExampleLayout.keepScreenOn = false
            } else {
                backImage.visibility = GONE
                fullScreenImg.visibility = GONE
                videoExampleLayout.keepScreenOn = true
            }
        }
    }

    private fun requestAudioFocusForMyApp(context: Context): Boolean {
        val am: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Request audio focus for playback
        val result: Int = am.requestAudioFocus(
            null,  // Use the music stream.
            AudioManager.STREAM_MUSIC,  // Request permanent focus.
            AudioManager.AUDIOFOCUS_GAIN
        )
        return if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d("AudioFocus", "Audio focus received")
            true
        } else {
            Log.d("AudioFocus", "Audio focus NOT received")
            false
        }
    }


    fun releaseAudioFocusForMyApp(context: Context) {
        val am =
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.abandonAudioFocus(null)
    }

}
