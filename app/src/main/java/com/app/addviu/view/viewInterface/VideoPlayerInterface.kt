package com.app.addviu.view.viewInterface

import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ui.PlayerView

interface VideoPlayerInterface {

    fun makeFullScreen(videoExampleLayout:ConstraintLayout,videoContainer:PlayerView,
                       window:Window,isFullscreen:Boolean)

    fun getRelatedVideos(uid:String)

    fun getRelatedComments(uid:String)

}