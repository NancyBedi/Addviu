package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.VideoPlayerScreen
import com.app.addviu.view.fragments.BaseFragment
import com.app.addviu.view.fragments.HomeFragment
import com.app.addviu.view.fragments.TrendingFragment
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.home_list_adapter.view.*

class SearchVidAdapter (
    private val imageLoader: ImageLoader,
    private var dashList: ArrayList<HomeData>, context: Context) :
    RecyclerView.Adapter<SearchVidAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.recycle_channel_video_item, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]


    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val textTitle = row.textTitle
        val detailText = row.detailText
        val userImage = row.userImage
        val moreIcon = row.moreIcon
        val bannerImage = row.bannerImage
        val textTime = row.textTime

        init {
            row.setOnClickListener(this)
        }


        override fun onClick(v: View?) {

        }

        fun updateList(homeList: ArrayList<HomeData>) {
            dashList = homeList
            notifyDataSetChanged()
        }
    }
}