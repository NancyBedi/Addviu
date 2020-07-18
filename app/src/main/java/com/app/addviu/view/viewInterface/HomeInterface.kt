package com.app.addviu.view.viewInterface

import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

interface HomeInterface {


    fun setBottomNavigationClicks(frameLayout: FrameLayout,
                             bottomNavigationView: BottomNavigationView,fragmentManager: FragmentManager)

    fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager)
}