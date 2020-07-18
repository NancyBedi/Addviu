package com.app.addviu.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.latestVidModel.LatestVidListData
import com.app.addviu.presenter.HomePresenter
import com.app.addviu.presenter.SideMenuPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.adapter.LatestVidAdapter
import kotlinx.android.synthetic.main.common_actionbar.*
import kotlinx.android.synthetic.main.home_fragment_layout.*

class SideMenuVid : BaseActivity() {

    var latestVidAdapter: LatestVidAdapter? = null
    private val sideMenuPresenter = SideMenuPresenter(this)
    var title = ""
    val arrayList = ArrayList<LatestVidListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu_vid)
        title = intent.getStringExtra("title")?:""

        backImage.setOnClickListener {
            finish()
        }
        textTitle.text = title
        latestVidAdapter = LatestVidAdapter(imageLoader, arrayList, this)
        recyclerView.adapter = latestVidAdapter

        when(title){
            "Latest Videos" ->{
                sideMenuPresenter.getLatestVid()
            }
            "Entertainment and Comedy" ->{
                sideMenuPresenter.getEntertainVid()
            }
            "Latest News" ->{
                sideMenuPresenter.getLatestNewsVid()
            }
            "Women's Special" ->{
                sideMenuPresenter.getWomenVid()
            }
            "Suggested Videos" ->{
                sideMenuPresenter.getSuggestVid()
            }
        }
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}