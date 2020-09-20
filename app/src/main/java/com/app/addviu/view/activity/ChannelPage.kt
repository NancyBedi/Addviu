package com.app.addviu.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.SIGN_IN_CODE
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.ChannelHomeAdapter
import com.app.addviu.view.fragments.ChannelAbout
import com.app.addviu.view.fragments.ChannelHome
import com.app.addviu.view.fragments.ChannelPlaylist
import com.app.addviu.view.fragments.ChannelVideo
import kotlinx.android.synthetic.main.activity_channel_home.*
import kotlinx.android.synthetic.main.activity_channel_home.backImage
import kotlinx.android.synthetic.main.activity_channel_home.tabLayout
import kotlinx.android.synthetic.main.activity_channel_home.viewPager

class ChannelPage : BaseActivity() {
    var name = ""
    var channelId = ""
    var banner = ""
    var coverImg = ""
    var isUserChannel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_home)

        name = intent.getStringExtra("name")?:""
        channelId = intent.getStringExtra("id")?:""
//        banner = intent.getStringExtra("banner")?:""
//        coverImg = intent.getStringExtra("coverImg")?:""
        isUserChannel = intent.getBooleanExtra("userChannel", false)
        textTitle.text = name
        val adapter = ChannelHomeAdapter(getSupportFragmentManager())
        adapter.addFragment(ChannelHome(channelId, isUserChannel), "HOME")
        adapter.addFragment(ChannelVideo(channelId, isUserChannel), "VIDEOS")
        adapter.addFragment(ChannelPlaylist(channelId, isUserChannel), "PLAYLIST")
        adapter.addFragment(ChannelAbout(), "ABOUT")

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)
        if (isUserChannel){
            viewPager.setCurrentItem(1, true)
        }
        backImage.setOnClickListener {
            finish()
        }

        uploadIcon.setOnClickListener {
            if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                startActivity(Intent(this, VideoUploadScreen::class.java))
            } else {
                startActivityForResult(Intent(this, SignInScreen::class.java), SIGN_IN_CODE)
            }
//            startActivity(Intent(this,VideoUploadScreen::class.java))
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}