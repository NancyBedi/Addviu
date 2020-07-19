package com.app.addviu.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.IS_SIGN_CLICKED
import com.app.addviu.presenter.HomePresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.fragments.AccountFragment
import com.app.addviu.view.fragments.HomeFragment
import com.google.android.material.navigation.NavigationView
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*


class HomeScreen : BaseActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private val homePresenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        initViews()
        setClickListeners()
    }

    private fun setClickListeners() {
        menuIcon.setOnClickListener(this)
        uploadIcon.setOnClickListener(this)
        searchIcon.setOnClickListener(this)
        closeIcon.setOnClickListener(this)
    }

    private fun initViews() {
        val homeFragment = HomeFragment(this)
        homePresenter.previousFragment = homeFragment
        homePresenter.loadFragment(homeFragment, supportFragmentManager)
        homePresenter.setBottomNavigationClicks(frameLayout, bottomNavView, supportFragmentManager)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.menuIcon -> {
                val valX: Float = navigationView.x
                if (valX < 0) {
                    drawerLayout.openDrawer(navigationView)
                } else {
                    drawerLayout.closeDrawer(navigationView)
                }
            }

            R.id.uploadIcon -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    startActivity(Intent(this, VideoUploadScreen::class.java))
                } else {
                    startActivity(Intent(this, SignInScreen::class.java))
                }
            }

            R.id.searchIcon -> {
                closeIcon.visibility = VISIBLE
                editSearch.visibility = VISIBLE
                textView.visibility = GONE
                searchIcon.visibility = GONE
            }

            R.id.closeIcon -> {
                searchIcon.visibility = VISIBLE
                closeIcon.visibility = GONE
                editSearch.visibility = GONE
                textView.visibility = VISIBLE
                editSearch.setText("")
            }
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onResume() {
        super.onResume()

        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {

            imageLoader.displayImage(
                "https://addviu.com/storage/images/channels/Food Masala_1582567340_13511259285e540fac3d6ca.webP",
                userImage,
                roundProfilePic()
            )
            bottomNavView.menu.findItem(R.id.signInMenu).setIcon(null)
            bottomNavView.menu.findItem(R.id.signInMenu).title = ""
            bottomNavView.menu.findItem(R.id.highViewMenu).setIcon(R.drawable.notification)
            bottomNavView.menu.findItem(R.id.highViewMenu).title = "Notifications"
            if (sharedPrefsHelper?.get(IS_SIGN_CLICKED, false)!!) {
                homePresenter.loadFragment(AccountFragment(), supportFragmentManager)
                sharedPrefsHelper?.put(IS_SIGN_CLICKED, false)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        fragment?.onActivityResult(requestCode, resultCode, data)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(navigationView)
        val intent = Intent(this, SideMenuVid::class.java)
        when (item.itemId) {
            R.id.navigation_latest_videos -> {
                intent.putExtra("title", "Latest Videos")
            }
            R.id.navigation_entertainment -> {
//                val intent = Intent(this, SideMenuVid::class.java)
                intent.putExtra("title", "Entertainment and Comedy")
//                startActivity(intent)
            }
            R.id.navigation_latest_news -> {
                intent.putExtra("title", "Latest News")
            }
            R.id.navigation_women -> {
                intent.putExtra("title", "Women's Special")
            }
            R.id.navigation_suggested -> {
                intent.putExtra("title", "Suggested Videos")
            }
        }
        startActivity(intent)
        return true
    }


}