package com.app.addviu.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.app.addviu.AppController
import com.app.addviu.model.videoModel.CategoryBean
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.adapter.ChannelPagerAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.RewardsInterface
import com.google.android.material.tabs.TabLayout

class ChannelPresenter(val context: Context): ResponseCallback, RewardsInterface {

    override fun setUpViewPagerwithTabLayout(
        viewPager: ViewPager,
        fragmentManager: FragmentManager,
        tabLayout: TabLayout) {
        val channelPagerAdapter = ChannelPagerAdapter(fragmentManager)
        viewPager.adapter = channelPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun getCategories() {
        AppController.instance?.dataManager?.getCategories(this, context)
    }

    override fun <T> success(t: T) {
        if (t is CategoryBean) {
            if (t.status == 1) {
                (context as MyChannels).categoryList.clear()
                context.categoryList.addAll(t.data)
                context.showCategoryDialog(t.data)
            }
        }
    }

    override fun failure(t: Throwable) {

    }


}