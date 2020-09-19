package com.app.addviu.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.FILTER
import com.app.addviu.model.KeywordBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.searchFilterModel.SearchFilterBean
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.adapter.KeywordAdapter
import com.app.addviu.view.adapter.SearchFilterAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.home_screen_actionbar.editSearch
import kotlinx.android.synthetic.main.home_screen_actionbar.menuIcon

class SearchActivity : BaseActivity(), View.OnClickListener, ResponseCallback, TextWatcher {
    //    private var homeAdapter: SearchVidAdapter? = null
    private var homeAdapter: SearchFilterAdapter<*>? = null
    private var keywordAdapter: KeywordAdapter<*>? = null
    private val arrayList = ArrayList<HomeData>()
    var savedKeyWord = ArrayList<KeywordBean>()
    lateinit var gson: Gson

    //    var selectedPosition = 0
//    var linearLayoutManager: LinearLayoutManager? = null
//    private val PAGE_START = 1
//    private var currentPage = PAGE_START
//
//    private var visibleThreshold = 0
//    private var lastVisibleItem = 0
//    private var totalItemCount: Int = 0
//    private var isLoading = false
//    var totalItemsAvailable = 0
//    var lastPage = 0
    var type = ""
    var upload = ""
    var isFirst = true
    var noDataFound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setClicks()
        gson = Gson()
        val response = sharedPrefsHelper!!["keywords", ""]
        if (!response.equals("")) {
            savedKeyWord = gson.fromJson(
                response,
                object : TypeToken<List<KeywordBean?>?>() {}.type
            )
        }
        if (isFirst && savedKeyWord.size > 0) {
            editSearch.addTextChangedListener(this)
            isFirst = false
            searched.visibility = VISIBLE
            txtNoData.visibility = GONE
            keywordAdapter = KeywordAdapter(savedKeyWord, this, imageLoader, "keyword")
            searched.adapter = keywordAdapter
        }else{
            txtNoData.text = "Please Search with some keyword!!"
        }
        editSearch.requestFocus()
//        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        searched.layoutManager = linearLayoutManager
//        homeAdapter = SearchVidAdapter(imageLoader, arrayList, this)
    }

    fun setClicks() {
        oNsearch()
        menuIcon.setOnClickListener(this)
        filter.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.menuIcon -> {
                finish()
                overridePendingTransition(R.anim.no_anim, R.anim.fade_out)
            }
            R.id.filter -> {
                val intent = Intent(this, FilterActivity::class.java)
                startActivityForResult(intent, FILTER)
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in)
            }
        }
    }

    fun oNsearch() {
        editSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!editSearch.text.toString().isNullOrEmpty()) {
                    search(type, upload)
                    addKeyword(editSearch.text.toString())
                } else {
                    Util.showToast("Enter a keyword first.", this)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun <T> success(t: T) {
        if (t is SearchFilterBean) {
//            totalItemsAvailable = t.success.total
//            lastPage = t.success.lastPage
//            visibleThreshold = t.success.perPage
//            setDataInList(t.success.data)
            searched.visibility = VISIBLE
            txtNoData.visibility = GONE

            if (t.videos.size > 0) {
                noDataFound = false
//                removeTextChange()
                homeAdapter = SearchFilterAdapter(t.videos, this, imageLoader, "video")
                searched.adapter = homeAdapter
            } else if (t.channels.size > 0) {
                noDataFound = false
//                removeTextChange()
                homeAdapter = SearchFilterAdapter(t.channels, this, imageLoader, "channel")
                searched.adapter = homeAdapter
            } else if (t.playlists.size > 0) {
                noDataFound = false
//                removeTextChange()
                homeAdapter = SearchFilterAdapter(t.playlists, this, imageLoader, "playlist")
                searched.adapter = homeAdapter
            } else {
                noDataFound = true
//                editSearch.addTextChangedListener(this)
                searched.visibility = GONE
                txtNoData.text = "No Data Found Please try with some other keyword!!"
                txtNoData.visibility = VISIBLE
            }
            filter.visibility = VISIBLE
        }
    }

    override fun failure(t: Throwable) {
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FILTER -> {
                    type = data?.getStringExtra("type") ?: ""
                    upload = data?.getStringExtra("upload") ?: ""
                    if (!editSearch.text.toString().isNullOrEmpty()) {
                        search(type, upload)
                    } else {
                        Util.showToast("Enter a keyword first.", this)
                    }
                }
            }
        }
    }

    private fun setDataInList(homeList: ArrayList<HomeData>) {
//        if (!isLoading)
        arrayList.clear()
        arrayList.addAll(homeList)
        homeAdapter?.notifyDataSetChanged()

//        isLoading = false
    }

    fun addKeyword(keyword: String) {
        var checkSearchList = ArrayList<KeywordBean>()
        var isMatched = false
        val response = sharedPrefsHelper!!["keywords", ""]
        if (!response.equals("")) {
            checkSearchList = gson.fromJson(
                response,
                object : TypeToken<List<KeywordBean?>?>() {}.type
            )
        }
        for (i in checkSearchList.indices) {
            if (checkSearchList[i].keyword.equals(keyword)) {
                isMatched = true
            }
        }
        if (!isMatched) {
            val keywordBean = KeywordBean()
            keywordBean.keyword = keyword
            checkSearchList.add(0, keywordBean)
            if (checkSearchList.size > 15)
                checkSearchList.removeAt(checkSearchList.size - 1)
            val json: String = gson.toJson(checkSearchList)
            sharedPrefsHelper!!.put("keywords", json)
        }
    }

    fun search(vType: String, uDate: String) {
        val map = HashMap<String, String>()
        map.put("searchKeyword", editSearch.text.toString())
        map.put("type", vType)
        map.put("uploaddate", uDate)
        AppController.instance?.dataManager?.search(map, this, this)
    }

//    private fun addTextChangeListener() {
//        editSearch.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//
//            override fun afterTextChanged(editable: Editable) {}
//        })
//    }

    fun filterKeyList(
        charString: String,
        arrayList: ArrayList<KeywordBean>,
        adapter: KeywordAdapter<*>
    ) {
        val filteredList = ArrayList<KeywordBean>()

        for (data in arrayList) {
            if (data.keyword.toLowerCase().contains(charString.toLowerCase())) {
                filteredList.add(data)
            }
        }
        adapter.updateKeyList(filteredList)
    }

    fun removeTextChange(){
        editSearch.addTextChangedListener(null)
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (charSequence.toString().equals("") && noDataFound){
            searched.visibility = VISIBLE
            txtNoData.visibility = GONE
            keywordAdapter = KeywordAdapter(savedKeyWord, this@SearchActivity, imageLoader, "keyword")
            searched.adapter = keywordAdapter
        }else{
//            editSearch.addTextChangedListener(null)
            filterKeyList(charSequence.toString(), savedKeyWord, keywordAdapter!!)
        }
    }
}