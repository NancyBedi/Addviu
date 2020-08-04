package com.app.addviu.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.IS_SIGN_CLICKED
import com.app.addviu.data.helper.SIGN_IN_CODE
import com.app.addviu.data.helper.USER_IMAGE
import com.app.addviu.presenter.HomePresenter
import com.app.addviu.view.BaseActivity
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*


class HomeScreen : BaseActivity(), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    var count = 0
    private val homePresenter = HomePresenter(this,supportFragmentManager)

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
//        closeIcon.setOnClickListener(this)
    }

    fun setHomeSelected() {
        bottomNavView.selectedItemId = R.id.homeMenu
    }

    fun setTrendingSelected() {
        bottomNavView.selectedItemId = R.id.trendingMenu
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        bottomNavView.setOnNavigationItemSelectedListener(homePresenter)
        bottomNavView.selectedItemId = R.id.homeMenu
        loadIconsAfterLogin()
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
                    startActivityForResult(Intent(this, SignInScreen::class.java), SIGN_IN_CODE)
                }
            }

            R.id.searchIcon -> {
                closeIcon.visibility = VISIBLE
                editSearch.visibility = VISIBLE
                textView.visibility = GONE
                searchIcon.visibility = GONE
            }

//            R.id.closeIcon -> {
//                searchIcon.visibility = VISIBLE
//                closeIcon.visibility = GONE
//                editSearch.visibility = GONE
//                textView.visibility = VISIBLE
//                editSearch.setText("")
//            }
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE && resultCode == Activity.RESULT_OK) {
            finish()
            startActivity(intent)
        } else if (requestCode == SIGN_IN_CODE && resultCode != Activity.RESULT_OK) {
            bottomNavView.menu.findItem(R.id.signInMenu).setIcon(R.drawable.signin)
            bottomNavView.menu.findItem(R.id.signInMenu).title = getString(R.string.sign_in)
        } else {
            val baseFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
            baseFragment?.onActivityResult(requestCode, resultCode, data)
        }
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


    private fun loadIconsAfterLogin() {
        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
//            bottomNavView.menu.findItem(R.id.homeMenu).isChecked = true
            bottomNavView.menu.findItem(R.id.signInMenu).setIcon(null)
            bottomNavView.menu.findItem(R.id.signInMenu).title = ""
            bottomNavView.menu.findItem(R.id.highViewMenu).setIcon(R.drawable.notification)
            bottomNavView.menu.findItem(R.id.highViewMenu).title = getString(R.string.notifications)
            if (sharedPrefsHelper?.get(USER_IMAGE, "")!!.isNotEmpty()) {
                imageLoader.displayImage(
                    sharedPrefsHelper?.get(USER_IMAGE, ""),
                    userImage,
                    roundProfilePic()
                )
            }else{
                imageLoader.displayImage(
                    "drawable://"+ R.drawable.circle_user,
                    userImage,
                    roundProfilePic()
                )
//                bottomNavView.menu.findItem(R.id.signInMenu).setIcon(R.drawable.circle_user)
            }
            if (sharedPrefsHelper?.get(IS_SIGN_CLICKED, false)!!) {
//                bottomNavView.selectedItemId = R.id.homeMenu
                sharedPrefsHelper?.put(IS_SIGN_CLICKED, false)
            }
        }
    }

    override fun onBackPressed() {
        count ++
        if (count == 2){
            count = 0
            finish()
        }else{
//            val valX: Float = navigationView.x
//            if (valX > 0) {
                drawerLayout.closeDrawer(navigationView)
//            }else{
                Util.showToast("Press Again to exit", this)
//            }
//            drawerLayout.closeDrawer(navigationView)
        }
    }
}