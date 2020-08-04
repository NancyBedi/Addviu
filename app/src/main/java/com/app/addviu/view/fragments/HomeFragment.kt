package com.app.addviu.view.fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.home_screen_actionbar.*


class HomeFragment(val context:HomeScreen) : BaseFragment(), ResponseCallback {

    private var homeAdapter: HomeListAdapter? = null
    private val arrayList = ArrayList<HomeData>()
    var selectedPosition = 0
    var canSubscribe = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppController.instance?.dataManager?.getHomeVideoData(this, activity!!)
        context.searchIcon.visibility = VISIBLE
        context.closeIcon.visibility = GONE
        context.editSearch.visibility = GONE
        context.textView.visibility = VISIBLE
        context.editSearch.setText("")
        return inflater.inflate(R.layout.home_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setProgressVisible(progress_circular, recyclerView)
        homeAdapter = HomeListAdapter(imageLoader, arrayList, activity!!, this)
        recyclerView.adapter = homeAdapter
        addTextChangeListener(context)
    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
        arrayList.clear()
        arrayList.addAll(homeList)
        homeAdapter?.notifyDataSetChanged()
    }

    override fun <T> success(t: T) {
//        setProgressGone(progress_circular, recyclerView)
        if (t is HomeBean) {
            setDataInList(t.data)
        }
    }

    override fun failure(t: Throwable) {
//        setProgressGone(progress_circular, recyclerView)
        Util.showToast(t.toString(), activity!!)
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
        homeAdapter?.notifyItemChanged(selectedPosition)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHANGE_HOME_DATA && resultCode == RESULT_OK) {
            val relatedVideo = data?.getParcelableExtra<RelatedVideo>("data")
            relatedVideo?.let { it ->
                changeData(it)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
            context.searchIcon.visibility = VISIBLE
            context.closeIcon.visibility = GONE
            context.editSearch.visibility = GONE
            context.textView.visibility = VISIBLE
            context.editSearch.setText("")
            AppController.instance?.dataManager?.getHomeVideoData(this, activity!!)
        }

//        context.editSearch.setOnTouchListener(View.OnTouchListener { v, event ->
//            val DRAWABLE_LEFT = 0
//            val DRAWABLE_TOP = 1
//            val DRAWABLE_RIGHT = 2
//            val DRAWABLE_BOTTOM = 3
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX >= context.editSearch.right - context.editSearch.compoundDrawables
//                        .get(DRAWABLE_RIGHT).bounds.width()
//                ) {
//                    val map = HashMap<String, String>()
//                    map.put("searchKeyword", context.editSearch.text.toString())
//                    AppController.instance?.dataManager?.search(map, this, activity)
//                    return@OnTouchListener true
//                }
//            }
//            false
//        })

//        context.editSearch.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                if (homeAdapter != null) {
//                    filterCarsList(charSequence.toString(), arrayList, homeAdapter!!)
//                }
//            }
//            override fun afterTextChanged(editable: Editable) {}
//        })
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

}