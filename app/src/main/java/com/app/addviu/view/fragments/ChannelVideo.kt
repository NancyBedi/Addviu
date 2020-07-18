package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.model.channelVideoModel.ChannelVideoBean
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.adapter.ChannelVidAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.channel_video_fragment.*

class ChannelVideo(val channelId:String):BaseFragment(), ResponseCallback {
    var adapter: ChannelListAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channel_video_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun <T> success(t: T) {
        if (t is ChannelVideoBean){
            if (t.status == 1){
                recycleVideo.adapter = ChannelVidAdapter(imageLoader, t.data, activity!!)
            }else{
                Util.showToast("No Videos found!!", activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            getVids()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        getVids()
//    }

    fun getVids(){
        AppController.instance?.dataManager?.channelVideos(channelId, this, null)
    }
}