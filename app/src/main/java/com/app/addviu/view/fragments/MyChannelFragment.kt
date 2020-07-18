package com.app.addviu.view.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.ChannelBean
import com.app.addviu.model.videoModel.ChannelData
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.activity.VideoUploadScreen
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_rewards_screen.*
import kotlinx.android.synthetic.main.add_channel_layout.*
import kotlinx.android.synthetic.main.my_channel_layout.*

class MyChannelFragment:BaseFragment(), ResponseCallback {
    var channelList = ArrayList<ChannelData>()
    var adapter: ChannelListAdapter?=null
    var channelData = ChannelData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_channel_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (isVisible){
//            showList()
//        }
    }

    override fun <T> success(t: T) {
        if (t is ChannelBean) {
            if (t.status == 1) {
                channelList.clear()
                channelList.addAll(t.data)
                if (isVisible) {
                    adapter = ChannelListAdapter(imageLoader, channelList, this, activity!!)
                    mychannelRecycle.adapter = adapter
                }
//                showChannelDialog(t.data)
            }
        } else if (t is SignUpBean){
            if (t.status == 1){
                Util.showToast(t.message, activity!!)
                showList()
            }else{
                Util.showToast(t.message, activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    fun showList(){
        AppController.instance?.dataManager?.getUserChannels(this, context)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            showList()
        }
    }

    public fun showDeleteDialog(context: Context, title:String, openClick: YesClick) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val alertLayout = inflater.inflate(R.layout.download_dialog, null)
        val msgAlert = alertLayout.findViewById<TextView>(R.id.msgAlert)
        val okButton = alertLayout.findViewById<TextView>(R.id.okBtnAlert)
        val oprnAlert = alertLayout.findViewById<TextView>(R.id.oprnAlert)

        val alert = android.app.AlertDialog.Builder(context)
        val a = alert.create()
        //        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        a.setView(alertLayout, 0, 0, 0, 0)
        a.show()
        msgAlert.text = title
        okButton.text = "NO"
        oprnAlert.text = "YES"
        okButton.setOnClickListener { a.dismiss() }

        oprnAlert.setOnClickListener {
            openClick.yesClick()
            a.dismiss()
        }
    }
}