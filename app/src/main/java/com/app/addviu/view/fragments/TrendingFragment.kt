package com.app.addviu.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.homeModel.TrendingBean
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.home_fragment_layout.*

class TrendingFragment:BaseFragment(),ResponseCallback {

    private var homeAdapter: HomeListAdapter? = null
    private val arrayList = ArrayList<HomeData>()
    var selectedPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppController.instance?.dataManager?.getTrendingVideoData(1,this, activity)
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setProgressVisible(progress_circular, recyclerView)
        homeAdapter = HomeListAdapter(imageLoader, arrayList, activity!!,this)
        recyclerView.adapter = homeAdapter

    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
        arrayList.clear()
        arrayList.addAll(homeList)
        homeAdapter?.notifyDataSetChanged()
    }

    override fun <T> success(t: T) {

        if(t is TrendingBean){
            setDataInList(t.data.data)
        }

    }

    override fun failure(t: Throwable) {
        Util.showToast(t.toString(), activity!!)
    }

    fun changeData(relatedVideo: RelatedVideo){
        val homeData = HomeData()
        homeData.uid = relatedVideo.uid
        homeData.title = relatedVideo.title
        homeData.channelName = relatedVideo.channel.channelName
        homeData.viewsCount = relatedVideo.viewsCount
        homeData.createdDate = relatedVideo.createdDate
        homeData.thumbnailUrl = relatedVideo.thumbnailUrl
        homeData.channelImage = relatedVideo.channel.coverImage
        arrayList[selectedPosition] = homeData
        homeAdapter?.notifyItemChanged(selectedPosition)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CHANGE_HOME_DATA && resultCode == Activity.RESULT_OK){
            val relatedVideo = data?.getParcelableExtra<RelatedVideo>("data")
            relatedVideo?.let { it ->
                changeData(it)
            }
        }
    }
}