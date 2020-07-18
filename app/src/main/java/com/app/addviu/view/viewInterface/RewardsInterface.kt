package com.app.addviu.view.viewInterface

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


interface RewardsInterface{

    fun setUpViewPagerwithTabLayout(viewPager: ViewPager,fragmentManager: FragmentManager,tabLayout: TabLayout)
    fun getCategories()


}