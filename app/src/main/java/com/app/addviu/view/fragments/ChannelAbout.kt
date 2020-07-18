package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.R
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback

class ChannelAbout:BaseFragment(), ResponseCallback {
    var adapter: ChannelListAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channel_about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun <T> success(t: T) {

    }

    override fun failure(t: Throwable) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
        }
    }
}