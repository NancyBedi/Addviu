package com.app.addviu.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.SIGN_IN_CODE
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.ChannelHomeAdapter
import com.app.addviu.view.fragments.*
import kotlinx.android.synthetic.main.activity_channel_home.backImage
import kotlinx.android.synthetic.main.activity_channel_home.tabLayout
import kotlinx.android.synthetic.main.activity_channel_home.viewPager
import kotlinx.android.synthetic.main.activity_my_profile.*

class ProfilePage : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val adapter = ChannelHomeAdapter(getSupportFragmentManager())
        adapter.addFragment(MyProfileFragment(), "My Profile")
        adapter.addFragment(SubscribeChannelFragment(), "Subscribed Channel")

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)
        setClicks()


    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.backImage ->{
                finish()
            }
            R.id.uploadImage ->{
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    startActivity(Intent(this, VideoUploadScreen::class.java))
                } else {
                    startActivityForResult(Intent(this, SignInScreen::class.java), SIGN_IN_CODE)
                }
            }
        }
    }

    fun setClicks(){
        backImage.setOnClickListener(this)
        uploadImage.setOnClickListener(this)
    }
}