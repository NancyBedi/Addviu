package com.app.addviu.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.homeModel.TrendingBean
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.adapter.PaginationAdapter
import com.app.addviu.view.viewInterface.PaginationScrollListener
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*

class TrendPagination(val context:HomeScreen):BaseFragment(),ResponseCallback {

    private var homeAdapter: HomeListAdapter? = null
    private val arrayList = ArrayList<HomeData>()
    var selectedPosition = 0

    private val TAG = "MainActivity"
    private val PAGE_START = 1

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private val TOTAL_PAGES = 5
    var adapter: PaginationAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
//    var rv: RecyclerView? = null
//    var main_progress: ProgressBar? = null
//    var error_layout: LinearLayout? = null
//    var btnRetry: Button? = null
//    var txtError: TextView? = null
//    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var isLoading = false
    var isLastPage = false
    private var currentPage = PAGE_START


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
        adapter = PaginationAdapter(activity, this)
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setProgressVisible(progress_circular, recyclerView)

        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(linearLayoutManager)
//        recyclerView.setItemAnimator(DefaultItemAnimator())
//        homeAdapter = HomeListAdapter(imageLoader, arrayList, activity!!,this)
        recyclerView.adapter = adapter
//        rv.setAdapter(adapter)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                this@TrendPagination.isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
        loadFirstPage()
        addTextChangeListener(context)

    }

    private fun loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ")

        // To ensure list is visible when retry button in error view is clicked
        hideErrorView()
        currentPage = PAGE_START
        callTopRatedMoviesApi()
//        callTopRatedMoviesApi().enqueue(object : Callback<TopRatedMovies?>() {
//            fun onResponse(
//                call: Call<TopRatedMovies?>?,
//                response: Response<TopRatedMovies?>?
//            ) {
//                hideErrorView()
//                val results: List<HomeData> = fetchResults(response)
//                main_progress.setVisibility(View.GONE)
//                adapter!!.addAll(results)
//                if (currentPage <= TOTAL_PAGES) adapter!!.addLoadingFooter() else isLastPage = true
//            }
//
//            fun onFailure(call: Call<TopRatedMovies?>?, t: Throwable) {
//                t.printStackTrace()
//                showErrorView(t)
//            }
//        })
    }

//    private fun fetchResults(response: Response<TopRatedMovies>): List<Result?>? {
//        val topRatedMovies: TopRatedMovies = response.body()
//        return topRatedMovies.getResults()
//    }

    private fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $currentPage")
        callTopRatedMoviesApi()
//        callTopRatedMoviesApi() {
//            fun onResponse(
//                call: Call<TopRatedMovies?>?,
//                response: Response<TopRatedMovies?>?
//            ) {
//            }

//            fun onFailure(call: Call<TopRatedMovies?>?, t: Throwable?) {
//                t!!.printStackTrace()
//                adapter!!.showRetry(true, fetchErrorMessage(t))
//            }
//        })
    }


    private fun callTopRatedMoviesApi() {
        AppController.instance?.dataManager?.getTrendingVideoData(currentPage,this, activity)

//        return movieService.getTopRatedMovies(
//            getString(R.string.my_api_key),
//            "en_US",
//            currentPage
//        )
    }


    fun retryPageLoad() {
        loadNextPage()
    }


    private fun showErrorView(throwable: Throwable?) {
        if (error_layout.getVisibility() === View.GONE) {
            error_layout.setVisibility(View.VISIBLE)
            main_progress.setVisibility(View.GONE)
//            txtError.setText(fetchErrorMessage(throwable))
        }
    }


//    private fun fetchErrorMessage(throwable: Throwable?): String? {
//        var errorMsg = resources.getString(R.string.error_msg_unknown)
//        if (!isNetworkConnected()) {
//            errorMsg = resources.getString(R.string.error_msg_no_internet)
//        } else if (throwable is TimeoutException) {
//            errorMsg = resources.getString(R.string.error_msg_timeout)
//        }
//        return errorMsg
//    }

    private fun hideErrorView() {
        if (error_layout.getVisibility() === View.VISIBLE) {
            error_layout.setVisibility(View.GONE)
            main_progress.setVisibility(View.VISIBLE)
        }
    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
//        arrayList.clear()
//        arrayList.addAll(homeList)

        hideErrorView()
//        val results: ArrayList<HomeData> = homeList
        main_progress.setVisibility(View.GONE)
        adapter!!.addAll(homeList)
        if (currentPage <= TOTAL_PAGES) adapter!!.addLoadingFooter() else isLastPage = true


        adapter!!.removeLoadingFooter()
        isLoading = false
//        val results: MutableList<HomeData?>? = fetchResults(response)
        adapter!!.addAll(homeList)

        if (currentPage !== TOTAL_PAGES) adapter!!.addLoadingFooter() else isLastPage = true
//        homeAdapter?.notifyDataSetChanged()
    }

    override fun <T> success(t: T) {

        if(t is TrendingBean){
            setDataInList(t.data.data)
        }else if (t is HomeBean) {
            setDataInList(t.data)
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

        context.closeIcon.setOnClickListener{
            context.searchIcon.visibility = View.VISIBLE
            context.closeIcon.visibility = View.GONE
            context.editSearch.visibility = View.GONE
            context.textView.visibility = View.VISIBLE
            context.editSearch.setText("")
            AppController.instance?.dataManager?.getTrendingVideoData(1,this, activity)
        }

    }

    fun filterCarsList(
        charString: String,
        arrayList: ArrayList<HomeData>,
        adapter: HomeListAdapter) {
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CHANGE_HOME_DATA && resultCode == Activity.RESULT_OK){
            val relatedVideo = data?.getParcelableExtra<RelatedVideo>("data")
            relatedVideo?.let { it ->
                changeData(it)
            }
        }
    }
}