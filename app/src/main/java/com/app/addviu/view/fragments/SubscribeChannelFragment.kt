package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.USER_ID
import com.app.addviu.model.subscribedChannel.SubscribedChannelBean
import com.app.addviu.view.adapter.SubscribedChannelAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.subscribed_channel_fragment.*

class SubscribeChannelFragment:BaseFragment(), ResponseCallback {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscribed_channel_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun <T> success(t: T) {
        if (t is SubscribedChannelBean){
            if (t.status == 1){
                if (t.data.size > 0){
                    recyclerView.adapter = SubscribedChannelAdapter(imageLoader, t.data, this, activity!!)
                }
            }else{
                Util.showToast("No Channels Found", activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {
    }

    fun getSubscribedChannels(){
        val map = HashMap<String, String>()
        map["user_id"] = sharedPrefsHelper?.get(USER_ID, 0).toString()
        AppController.instance?.dataManager?.userSubscribedChannels(map, this, activity)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            getSubscribedChannels()
        }
    }
}