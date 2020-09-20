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
import com.app.addviu.view.fragments.*
import kotlinx.android.synthetic.main.activity_channel_home.*

class Playlistpage : BaseActivity() {
    var name = ""
    var playlistId = ""
    var banner = ""
    var coverImg = ""
    var isUserChannel = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlistpage)
        name = intent.getStringExtra("name")?:""
        playlistId = intent.getStringExtra("id")?:""

        textTitle.text = name

        val adapter = ChannelHomeAdapter(getSupportFragmentManager())
        adapter.addFragment(PlaylistHome(playlistId, isUserChannel), "HOME")
        adapter.addFragment(PlaylistVideo(playlistId, isUserChannel), "VIDEOS")
        adapter.addFragment(ChannelAbout(), "ABOUT")

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)

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