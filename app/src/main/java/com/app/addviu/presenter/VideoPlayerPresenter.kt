package com.app.addviu.presenter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.addviu.view.viewInterface.VideoPlayerInterface
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerPresenter(val context: Context) : VideoPlayerInterface{

    override fun makeFullScreen(
        videoExampleLayout: ConstraintLayout,
        videoContainer: PlayerView,
        window: Window,isFullscreen:Boolean
    ) {
        val layoutParams = videoContainer.layoutParams as ConstraintLayout.LayoutParams
        if (isFullscreen) {
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = Color.TRANSPARENT
                }
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
        } else {
            layoutParams.height = 0
            layoutParams.width = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
                window.statusBarColor = Color.BLACK
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                )
            }
        }
//        for (i in 0 until videoExampleLayout.childCount) {
//            val view: View = videoExampleLayout.getChildAt(i)
//            // If it's not the video element, hide or show it, depending on fullscreen status.
//            if (view.id != R.id.videoContainer && view.id != R.id.fullScreenImg &&
//                view.id != R.id.backImage
//            ) {
//                if (isFullscreen) {
//                    view.visibility = View.GONE
//                } else {
//                    view.visibility = View.VISIBLE
//                }
//            }
//        }
    }

    override fun getRelatedVideos(uid:String) {
    }

    override fun getRelatedComments(uid: String) {
//        AppController.instance?.dataManager?.getRelatedComments(uid,this,context)
    }




}