package com.app.addviu.view.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.addviu.R
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.videoModel.CategoryData
import com.app.addviu.model.videoModel.ChannelData
import com.app.addviu.presenter.ChannelPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.fragments.AddChannelFragment
import com.app.addviu.view.fragments.MyChannelFragment
import com.app.addviu.view.searchDailog.OnSearchItemSelected
import com.app.addviu.view.searchDailog.SearchListItem
import com.app.addviu.view.searchDailog.SearchableDialog
import kotlinx.android.synthetic.main.activity_rewards_screen.*
import kotlinx.android.synthetic.main.add_channel_layout.*
import kotlinx.android.synthetic.main.common_actionbar.*
import kotlinx.android.synthetic.main.common_actionbar.backImage


class MyChannels : BaseActivity(), View.OnClickListener {
    private val channelPresenter = ChannelPresenter(this)
    var categoryList = ArrayList<CategoryData>()
    var categoryId = 0
    var channelId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_channels)
        textTitle.text = sharedPrefsHelper?.get(USER_NAME, "")
        initViews()
        setClickListeners()

//        actionBarT.text = getString(R.string.my_channels)
    }

    private fun setClickListeners() {
        backImage.setOnClickListener(this)
    }
    private fun initViews() {
        channelPresenter.setUpViewPagerwithTabLayout(viewPager, supportFragmentManager, tabLayout)
        viewPager.setCurrentItem(1, true)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.backImage -> {
                finish()
            }
        }
    }
    fun getData(channelData: ChannelData){
        tabLayout.getTabAt(0)?.text = "Edit Channel"
        viewPager.setCurrentItem(0, true)
        val page: Fragment? =
            supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager.toString() + ":" + 0)
        if (page != null) {
            (page as AddChannelFragment?)?.setData(channelData, true)
        }
//        val fragment = AddChannelFragment()
//        fragment.setData(channelData)
    }

    fun showCategoryDialog(arrayList: ArrayList<CategoryData>) {
        val searchListItems = ArrayList<SearchListItem>()
        for (i in arrayList.indices) {
            searchListItems.add(SearchListItem(arrayList[i].id, arrayList[i].name))
        }
        val searchableDialog = SearchableDialog(
            this, searchListItems, "Select Category",
            "Search Category", 1
        )
        searchableDialog.show()

        searchableDialog.setOnItemSelected(object : OnSearchItemSelected {
            override fun onClick(position: Int, searchListItem: SearchListItem?) {
                categoryEditText.setText(searchListItem?.title)
                categoryId = searchListItem?.id ?: 0
                searchableDialog.dismiss()
            }
        })
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun changeData(){
        tabLayout.getTabAt(0)?.text = "Add Channel"
    }
}
