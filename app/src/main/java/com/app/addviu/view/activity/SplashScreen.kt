package com.app.addviu.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.addviu.R
import com.app.addviu.view.BaseActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*


class SplashScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val animZoomIn: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        logoImage.startAnimation(animZoomIn)

        Handler().postDelayed({
            startActivity(Intent(this,HomeScreen::class.java))
            finish()
        },2000)
    }

    override fun setStatusBarGradiant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(android.R.color.white)

        }
    }
}
