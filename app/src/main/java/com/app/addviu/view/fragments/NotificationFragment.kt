package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.homeModel.TrendingBean
import com.app.addviu.model.notificationModel.NotificationBean
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.adapter.NotificationAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationFragment:BaseFragment(), ResponseCallback {
    var adapter: ChannelListAdapter?=null

    private var homeAdapter: HomeListAdapter? = null
    private val arrayList = ArrayList<HomeData>()
    var linearLayoutManager: LinearLayoutManager? = null
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var visibleThreshold = 0
    private var lastVisibleItem = 0
    private var totalItemCount: Int = 0
    private var isLoading = false
    var totalItemsAvailable = 0
    var lastPage = 0
    var selectedPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
            AppController.instance?.dataManager?.getNotifications(this, activity)
        }else{
            loadFirstPage()
        }
        return inflater.inflate(R.layout.activity_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
            setUpHIghlyViewd()
        }
    }

    override fun <T> success(t: T) {
        if (t is NotificationBean){
            if (t.status == 1){
                AppController.instance?.dataManager?.clearNotifications(this, null)
//                if(empty($notificationArray['comment_id']) && $notificationArray['id'] == $notificationArray['notification_video_id'] && $notificationArray['user_id'] == $notificationArray['notification_user_id']){  ** for published videos**    }
//                elseif(!empty($notificationArray['comment_id'])){     ** display comments***    }
//                else{ *** for subscribed vidoes ****}
                recycleNotify.adapter = NotificationAdapter(imageLoader, t.data.notifications, activity!!)
            }else{
                Util.showToast("No Notification found.", activity!!)
            }
        }else if (t is TrendingBean) {
            totalItemsAvailable = t.data.total
            lastPage = t.data.lastPage
            visibleThreshold = t.data.perPage
            setDataInList(t.data.data)
        }else if (t is CommonSuccess){
            if (t.status == 1){
                if (context is HomeScreen){
                    (context as HomeScreen).removeBadge()
                }
            }
        }
    }

//    fun changeData(homeData: HomeData) {
//        arrayList[selectedPosition] = homeData
//        homeAdapter?.notifyItemChanged(selectedPosition)
//    }

    override fun failure(t: Throwable) {

    }

    fun setUpHIghlyViewd(){
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycleNotify.layoutManager = linearLayoutManager
        homeAdapter = HomeListAdapter(imageLoader, arrayList, activity!!, this, "home")
        recycleNotify.adapter = homeAdapter

        recycleNotify.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                val lastVisibleItemarray = linearLayoutManager!!.findLastVisibleItemPosition()
                if (lastVisibleItemarray > 0) {
                    lastVisibleItem = if (lastVisibleItemarray == 3) {
                        lastVisibleItemarray
                    } else {
                        lastVisibleItemarray
                    }
                    if (totalItemCount < totalItemsAvailable) {
                        if (!isLoading && totalItemCount <= lastVisibleItem + 1) {
                            if (lastPage > currentPage) {
                                currentPage += 1
                                loadNextPage()
                                isLoading = true
                            }
                        }
                    }
                }
            }
        })
    }

    private fun loadFirstPage() {
        AppController.instance?.dataManager?.highlyViewedVideos(1, this, activity)
    }

    private fun loadNextPage() {
        AppController.instance?.dataManager?.highlyViewedVideos(currentPage, this, activity)
    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
        if (!isLoading)
            arrayList.clear()
        arrayList.addAll(homeList)
        homeAdapter?.notifyDataSetChanged()
        isLoading = false
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isVisibleToUser) {
//
//        }
//    }
}