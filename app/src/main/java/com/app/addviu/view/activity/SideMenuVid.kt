package com.app.addviu.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.latestVidModel.LatestVidListData
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.presenter.SideMenuPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.LatestVidAdapter
import kotlinx.android.synthetic.main.common_actionbar.*
import kotlinx.android.synthetic.main.home_fragment_layout.*

class SideMenuVid : BaseActivity() {

    var latestVidAdapter: LatestVidAdapter? = null
    private val sideMenuPresenter = SideMenuPresenter(this)
    var title = ""
    val arrayList = ArrayList<HomeData>()
    public var visibleThreshold = 0
    private var lastVisibleItem = 0
    private var totalItemCount: Int = 0
    private var isLoading = false
    var totalItemsAvailable = 0
    var lastPage = 0
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    var linearLayoutManager: LinearLayoutManager? = null
    var selectedPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu_vid)
        title = intent.getStringExtra("title")?:""

        backImage.setOnClickListener {
            finish()
        }
        textTitle.text = title

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        latestVidAdapter = LatestVidAdapter(imageLoader, arrayList, this)
        recyclerView.adapter = latestVidAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager!!.itemCount
                var lastVisibleItemarray = linearLayoutManager!!.findLastVisibleItemPosition()
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
                                loadNextPage(currentPage)
                                isLoading = true
                            }
                        }
                    }
                }
            }
        })

        loadFirstPage()
    }

    public fun setDataInList(homeList: ArrayList<HomeData>) {
        if (!isLoading)
            arrayList.clear()
        arrayList.addAll(homeList)
        latestVidAdapter?.notifyDataSetChanged()
        isLoading = false
    }


    fun changeData(relatedVideo: RelatedVideo) {
        val homeData = HomeData()
        homeData.uid = relatedVideo.uid
        homeData.title = relatedVideo.title
        homeData.channelName = relatedVideo.channel.channelName
        homeData.viewsCount = relatedVideo.viewsCount
        homeData.createdDate = relatedVideo.createdDate
        homeData.thumbnailUrl = relatedVideo.thumbnailUrl
        homeData.channelImage = relatedVideo.channel.coverImage
        homeData.duration = relatedVideo.duration
        arrayList[selectedPosition] = homeData
        latestVidAdapter?.notifyItemChanged(selectedPosition)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHANGE_HOME_DATA && resultCode == RESULT_OK) {
            val relatedVideo = data?.getParcelableExtra<RelatedVideo>("data")
            relatedVideo?.let { it ->
                changeData(it)
            }
        }
    }

    private fun loadFirstPage() {
        when(title){
            "Latest Videos" ->{
                sideMenuPresenter.getLatestVid(1)
            }
            "Entertainment and Comedy" ->{
                sideMenuPresenter.getEntertainVid(1)
            }
            "Latest News" ->{
                sideMenuPresenter.getLatestNewsVid(1)
            }
            "Women's Special" ->{
                sideMenuPresenter.getWomenVid(1)
            }
            "Suggested Videos" ->{
                sideMenuPresenter.getSuggestVid(1)
            }
        }
    }

    private fun loadNextPage(currentPage:Int) {
        when(title){
            "Latest Videos" ->{
                sideMenuPresenter.getLatestVid(currentPage)
            }
            "Entertainment and Comedy" ->{
                sideMenuPresenter.getEntertainVid(currentPage)
            }
            "Latest News" ->{
                sideMenuPresenter.getLatestNewsVid(currentPage)
            }
            "Women's Special" ->{
                sideMenuPresenter.getWomenVid(currentPage)
            }
            "Suggested Videos" ->{
                sideMenuPresenter.getSuggestVid(currentPage)
            }
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}