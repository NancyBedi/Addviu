package com.app.addviu.view.adapter

import android.R.attr.displayOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.VideoPlayerScreen
import com.app.addviu.view.fragments.BaseFragment
import com.app.addviu.view.fragments.HomeFragment
import com.app.addviu.view.fragments.TrendingFragment
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.ImageSize
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.home_list_adapter.view.*
import kotlinx.android.synthetic.main.home_list_adapter.view.detailText
import kotlinx.android.synthetic.main.video_player_layout.*


class HomeListAdapter(
    private val imageLoader: ImageLoader,
    private val dashList: ArrayList<HomeData>,
    val context: Context,val baseFragment: BaseFragment
) :
    RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.home_list_adapter, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]

        holder.textTitle.text = data.title

        holder.detailText.text =
            data.channelName.plus(" . ").plus(data.viewsCount.toString()).plus(" views").
            plus(" . ").plus(data.createdDate)

        if(data.duration.isNotBlank() && data.duration.contains(":")) {
            val array = data.duration.split(":")
                if (array[0].equals("00") && array.size == 3) {
                    holder.textTime.text = array[1].plus(":").plus(array[2])
                }else {
                    holder.textTime.text = data.duration
                }
        }else{
            holder.textTime.text = data.duration
        }

//        val imageSize = ImageSize(200, 100)
//        imageLoader.loadImage(
//            data.thumbnailUrl,
//            imageSize,
//            (context as HomeScreen).profilePic(),
//            object : SimpleImageLoadingListener() {
//                override fun onLoadingComplete(
//                    imageUri: String,
//                    view: View?,
//                    loadedImage: Bitmap
//                ) {
//                    holder.bannerImage.setImageBitmap(loadedImage)
//                }
//            })
        imageLoader.displayImage(
            data.thumbnailUrl,
            holder.bannerImage,
            (context as HomeScreen).profilePic()
        )

        if(data.channelImage.isNullOrBlank()){
            holder.userImage.setImageResource(R.drawable.circle_user)
        }else {
            imageLoader.displayImage(data.channelImage, holder.userImage, context.roundProfilePic())
        }

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
            if(baseFragment is HomeFragment){
                baseFragment.selectedPosition = adapterPosition
            }else if(baseFragment is TrendingFragment){
                baseFragment.selectedPosition = adapterPosition
            }
            val data = dashList[adapterPosition]
            val intent = Intent(context, VideoPlayerScreen::class.java)
            intent.putExtra("uid", data.uid)
            (context as HomeScreen).startActivityForResult(intent, CHANGE_HOME_DATA)
        }
    }
}