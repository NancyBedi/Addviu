package com.app.addviu.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.view.activity.VideoPlayerScreen
import com.nostra13.universalimageloader.core.ImageLoader

import kotlinx.android.synthetic.main.related_video_adapter.view.*


class RelatedVideoAdapter(
    private val imageLoader: ImageLoader,
    private val dashList: ArrayList<HomeData>,
    val context: Context
) :
    RecyclerView.Adapter<RelatedVideoAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.related_video_adapter, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]

        holder.videoTitle.text = data.title

        holder.channelName.text = data.channelName

        holder.viewsCount.text = data.viewsCount.toString().plus(" views")

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
            (context as VideoPlayerScreen).profilePic()
        )


    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val videoTitle = row.videoTitle
        val channelName = row.channelName
        val moreIcon = row.moreIcon
        val bannerImage = row.bannerImage
        val viewsCount = row.textViews
        val textTime = row.textTime

        init {
            row.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            (context as VideoPlayerScreen).setRelatedVideoSelected(dashList[adapterPosition])
        }
    }
}