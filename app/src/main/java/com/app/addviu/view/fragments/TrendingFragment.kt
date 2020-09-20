package com.app.addviu.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.homeModel.TrendingBean
import com.app.addviu.model.homeVideoModel.HomeVideoBean
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.adapter.TrendAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*

class TrendingFragment(val context: HomeScreen) : BaseFragment(), ResponseCallback {

    private var homeAdapter: HomeListAdapter? = null
    private val arrayList = ArrayList<HomeData>()
    var selectedPosition = 0
    var linearLayoutManager: LinearLayoutManager? = null

    private val PAGE_START = 1
    private var currentPage = PAGE_START

    private var visibleThreshold = 0
    private var lastVisibleItem = 0
    private var totalItemCount: Int = 0
    private var isLoading = false
    var totalItemsAvailable = 0
    var lastPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as HomeScreen).searchIcon.visibility = View.VISIBLE
        (context as HomeScreen).closeIcon.visibility = View.GONE
        (context as HomeScreen).editSearch.visibility = View.GONE
        (context as HomeScreen).textView.visibility = View.VISIBLE
        (context as HomeScreen).editSearch.setText("")
        loadFirstPage()
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        homeAdapter = HomeListAdapter(imageLoader, arrayList, activity!!, this, "home")
        recyclerView.adapter = homeAdapter

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
                                loadNextPage()
                                isLoading = true
                            }
                        }
                    }
                }
            }
        })

        addTextChangeListener(context)

    }

    private fun loadFirstPage() {
        AppController.instance?.dataManager?.getTrendingVideoData(1, this, activity)
    }

    private fun loadNextPage() {
        AppController.instance?.dataManager?.getTrendingVideoData(currentPage, this, activity)
    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
        if (!isLoading)
            arrayList.clear()
        arrayList.addAll(homeList)
        homeAdapter?.notifyDataSetChanged()
        isLoading = false
    }


    override fun <T> success(t: T) {

        if (t is TrendingBean) {
            totalItemsAvailable = t.data.total
            lastPage = t.data.lastPage
            visibleThreshold = t.data.perPage
            setDataInList(t.data.data)
        } else if (t is HomeVideoBean) {
            totalItemsAvailable = t.success.total
            lastPage = t.success.lastPage
            visibleThreshold = t.success.perPage
            setDataInList(t.success.data)
        }
    }

    override fun failure(t: Throwable) {
        Util.showToast(t.toString(), activity!!)
    }

    fun changeData(homeData: HomeData) {
        arrayList[selectedPosition] = homeData
        homeAdapter?.notifyItemChanged(selectedPosition)
    }

    private fun addTextChangeListener(context: HomeScreen) {
        context.editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val map = HashMap<String, String>()
                map.put("searchKeyword", context.editSearch.text.toString())
                AppController.instance?.dataManager?.search(map, this, activity)
                return@OnEditorActionListener true
            }
            false
        })

        context.closeIcon.setOnClickListener {
            context.searchIcon.visibility = View.VISIBLE
            context.closeIcon.visibility = View.GONE
            context.editSearch.visibility = View.GONE
            context.textView.visibility = View.VISIBLE
            context.editSearch.setText("")
            loadFirstPage()
//            AppController.instance?.dataManager?.getTrendingVideoData(1, this, activity)
        }
    }

    fun filterCarsList(
        charString: String,
        arrayList: ArrayList<HomeData>,
        adapter: HomeListAdapter
    ) {
        val filteredList = ArrayList<HomeData>()
        for (data in arrayList) {
            if (data.title.toLowerCase().contains(charString.toLowerCase()) or
                data.channelName.toLowerCase().contains(charString.toLowerCase())
//                data.description.toLowerCase().contains(charString.toLowerCase())
            ) {
                filteredList.add(data)
            }
        }
        adapter.updateList(filteredList)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == CHANGE_HOME_DATA && resultCode == Activity.RESULT_OK) {
//            val relatedVideo = data?.getParcelableExtra<RelatedVideo>("data")
//            relatedVideo?.let { it ->
//                changeData(it)
//            }
//        }
//    }
}