package com.app.addviu.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.ChannelHomeAdapter
import com.app.addviu.view.fragments.*
import kotlinx.android.synthetic.main.activity_channel_home.*

class Playlistpage : BaseActivity() {
    var name = ""
    var playlistId = ""
    var banner = ""
    var coverImg = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlistpage)
        name = intent.getStringExtra("name")?:""
        playlistId = intent.getStringExtra("id")?:""

        textTitle.text = name

        val adapter = ChannelHomeAdapter(getSupportFragmentManager())
        adapter.addFragment(PlaylistHome(playlistId), "HOME")
        adapter.addFragment(PlaylistVideo(playlistId), "VIDEOS")
        adapter.addFragment(ChannelAbout(), "ABOUT")

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)

        backImage.setOnClickListener {
            finish()
        }

        uploadIcon.setOnClickListener {
            startActivity(Intent(this,VideoUploadScreen::class.java))
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}