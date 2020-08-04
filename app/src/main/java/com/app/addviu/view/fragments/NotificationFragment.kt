package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.model.notificationModel.NotificationBean
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.adapter.NotificationAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_notification.*

class
NotificationFragment:BaseFragment(), ResponseCallback {
    var adapter: ChannelListAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
            AppController.instance?.dataManager?.getNotifications(this, activity)
        }
        return inflater.inflate(R.layout.activity_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun <T> success(t: T) {
        if (t is NotificationBean){
            if (t.status == 1){
                recycleNotify.adapter = NotificationAdapter(imageLoader, t.data.notifications, activity!!)
            }else{
                Util.showToast("No Notification found.", activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            AppController.instance?.dataManager?.clearNotifications(this, null)
        }
    }
}