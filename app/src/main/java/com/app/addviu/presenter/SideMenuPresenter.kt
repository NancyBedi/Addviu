package com.app.addviu.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.app.addviu.AppController
import com.app.addviu.model.latestVidModel.LatestVidBean
import com.app.addviu.model.videoModel.CategoryBean
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.activity.SideMenuVid
import com.app.addviu.view.adapter.ChannelPagerAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.RewardsInterface
import com.app.addviu.view.viewInterface.SideMenuInterface
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.tabs.TabLayout

class SideMenuPresenter(val context: Context): ResponseCallback, SideMenuInterface {
    override fun <T> success(t: T) {
        if (t is LatestVidBean) {
            if (t.status == 1) {
                (context as SideMenuVid).arrayList.clear()
                (context).arrayList.addAll(t.data.data)
                (context).latestVidAdapter?.notifyDataSetChanged()
            }else{
                Util.showToast(t.message, context)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    override fun getLatestVid() {
        AppController.instance?.dataManager?.latesVideos(this, context)
    }

    override fun getEntertainVid() {
        AppController.instance?.dataManager?.entertainmentAndComedyVideos(this, context)
    }

    override fun getLatestNewsVid() {
        AppController.instance?.dataManager?.latestNewsVideos(this, context)
    }

    override fun getWomenVid() {
        AppController.instance?.dataManager?.womenSpecialVideos(this, context)
    }

    override fun getSuggestVid() {
        AppController.instance?.dataManager?.suggestedVideos(this, context)
    }


}