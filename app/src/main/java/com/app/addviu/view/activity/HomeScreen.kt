package com.app.addviu.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.IS_SIGN_CLICKED
import com.app.addviu.data.helper.SIGN_IN_CODE
import com.app.addviu.data.helper.USER_IMAGE
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.presenter.HomePresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.fragments.*
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.navigation.NavigationView
import com.hoanganhtuan95ptit.draggable.DraggablePanel
import com.hoanganhtuan95ptit.draggable.utils.translationYAnim
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*
import kotlinx.android.synthetic.main.custom_badge_layout.view.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import kotlinx.android.synthetic.main.layout_top.*
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.*


class HomeScreen : BaseActivity(), OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {
    var notificationsBadge: View? = null
    var count = 0
    private val homePresenter = HomePresenter(this, supportFragmentManager)

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
        ivPlay.setOnClickListener(this)
        ivPause.setOnClickListener(this)
        ivClose.setOnClickListener(this)
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val orientation = resources.configuration.orientation
        val isLandscape =
            orientation == Configuration.ORIENTATION_LANDSCAPE
        if (isLandscape) {
            draggablePanel.mTempHeight = MATCH_PARENT
        } else {
            draggablePanel.mTempHeight = WRAP_CONTENT
        }

    }

    fun initializeDraggablePanel(homeData: HomeData) {

        draggablePanel.maximize()

        homeData.viewsCount = homeData.viewsCount + 1

        val baseFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        if (baseFragment is TrendingFragment) {
            baseFragment.changeData(homeData)
        }else if(baseFragment is HomeFragment){
            baseFragment.changeData(homeData)
        }

        tvTitle.text = homeData.title

        val bundle1 = Bundle()
        bundle1.putParcelable("data", homeData)

        val videoPlayerFragment = VideoPlayerFragment(draggablePanel)
        videoPlayerFragment.arguments = bundle1

        val bundle = Bundle()
        bundle.putString("uid", homeData.uid)
        val videoDetailsFragment = VideoDetailsFragment()
        videoDetailsFragment.arguments = bundle

        if (draggablePanel.mCurrentState == DraggablePanel.State.MIN) {
            removeVideoPlayer()
        }

        supportFragmentManager.beginTransaction().replace(R.id.frameTop, videoPlayerFragment)
            .commit() // add frame top
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameBottom, videoDetailsFragment, "detailsFragment")
            .commit() // add frame bottom


        draggablePanel.setDraggableListener(object : DraggablePanel.DraggableListener {

            override fun onChangeState(state: DraggablePanel.State) {
                super.onChangeState(state)
                if (state == DraggablePanel.State.MIN) {
                    (getTopFragment() as VideoPlayerFragment).hideControls()
//                    if((getTopFragment() as VideoPlayerFragment).player?.isPlaying!!){
//                        onPlayVideo()
//                    }else{
//                        onPauseVideo()
//                    }
                } else if (state == DraggablePanel.State.MAX) {
                    (getTopFragment() as VideoPlayerFragment).showControls()
                }
            }

        })
    }

    fun removeVideoPlayer() {
        (getTopFragment() as VideoPlayerFragment).releasePlayer()
        (getTopFragment() as VideoPlayerFragment).adsLoader?.release()
    }

    fun getTopFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.frameTop)
    }

    fun onPlayVideo(){
        ivPause.visibility = VISIBLE
        ivPlay.visibility = GONE
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
            R.id.ivPlay -> {
                onPlayVideo()
                (getTopFragment() as VideoPlayerFragment).playVideo()
            }
            R.id.ivPause -> {
                onPauseVideo()
                (getTopFragment() as VideoPlayerFragment).pauseVideo()
            }
            R.id.ivClose -> {
                removeVideoPlayer()
                draggablePanel.close()
            }

            R.id.uploadIcon -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    startActivity(Intent(this, VideoUploadScreen::class.java))
                } else {
                    startActivityForResult(Intent(this, SignInScreen::class.java), SIGN_IN_CODE)
                }
            }

            R.id.searchIcon -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in,R.anim.no_anim)

//                closeIcon.visibility = VISIBLE
//                        editSearch.visibility = VISIBLE
//                textView.visibility = GONE
//                searchIcon.visibility = GONE
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

    fun onPauseVideo(){
        ivPause.visibility = GONE
        ivPlay.visibility = VISIBLE

    }

//    override fun setFullScreen() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
//    }

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
                    Util.roundProfilePic()
                )
            }else{
                imageLoader.displayImage(
                    "drawable://"+ R.drawable.circle_user,
                    userImage,
                    Util.roundProfilePic()
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
        if (count == 1) {
            removeVideoPlayer()
            count = 0
            finish()
        } else {
            if (draggablePanel.mCurrentState == DraggablePanel.State.MAX) {
                draggablePanel.minimize()
            } else {
                count++
                drawerLayout.closeDrawer(navigationView)
                Util.showToast("Press Again to exit", this)
            }
        }
    }

    public fun getBadge(): View {
        if (notificationsBadge != null) {
            return notificationsBadge!!
        }
        val mbottomNavigationMenuView = bottomNavView.getChildAt(0) as BottomNavigationMenuView
        notificationsBadge = LayoutInflater.from(this).inflate(
            R.layout.custom_badge_layout,
            mbottomNavigationMenuView, false
        )
        return notificationsBadge!!
    }

    fun addBadge(count: String) {
        getBadge()
        notificationsBadge?.badge?.text = count
        bottomNavView?.addView(notificationsBadge)

    }

    fun removeBadge() {
        bottomNavView.removeView(notificationsBadge)
    }

    override fun onResume() {
        super.onResume()
        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
            homePresenter.notificationCount()
            if (sharedPrefsHelper?.get(USER_IMAGE, "")!!.isNotEmpty()) {
                imageLoader.displayImage(
                    sharedPrefsHelper?.get(USER_IMAGE, ""),
                    userImage,
                    Util.roundProfilePic()
                )
            }else{
                imageLoader.displayImage(
                    "drawable://"+ R.drawable.circle_user,
                    userImage,
                    Util.roundProfilePic()
                )
            }
        }
//        loadIconsAfterLogin()
    }
}