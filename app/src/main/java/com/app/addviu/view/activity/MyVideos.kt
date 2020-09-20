package com.app.addviu.view.activity

import android.os.Bundle
import com.app.addviu.R
import com.app.addviu.view.BaseActivity

class MyVideos : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_videos)

    }

//    fun getVids(){
//        AppController.instance?.dataManager?.channelVideos(channelId, this, null)
//    }
}