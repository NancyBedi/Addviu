package com.app.addviu.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.app.addviu.view.adapter.RewardsPagerAdapter
import com.app.addviu.view.viewInterface.RewardsInterface
import com.google.android.material.tabs.TabLayout

class RewardsPresenter(val context: Context):RewardsInterface {


    override fun setUpViewPagerwithTabLayout(
        viewPager: ViewPager,
        fragmentManager: FragmentManager,
        tabLayout: TabLayout
    ) {
        val rewardsPagerAdapter = RewardsPagerAdapter(fragmentManager)
        viewPager.adapter = rewardsPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun getCategories() {

    }


}