package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.KeywordBean
import com.app.addviu.model.searchFilterModel.ChannelFilter
import com.app.addviu.model.searchFilterModel.PlaylistFilter
import com.app.addviu.model.searchFilterModel.VideoFilter
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.activity.Playlistpage
import com.app.addviu.view.activity.SearchActivity
import com.app.addviu.view.activity.VideoPlayerScreen
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.*
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.btnMore
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.thumbnail
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.txtsubscriber
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.videoTitle
import kotlinx.android.synthetic.main.recycle_keyword_item.view.*
import kotlinx.android.synthetic.main.recycle_mychannel_item.view.*
import kotlinx.android.synthetic.main.recycle_playlist_item.view.*

class KeywordAdapter<Any>(
    private var dashList: ArrayList<Any>,
    val context: Context,
    val imageLoader: ImageLoader,
    private val type: String
) : RecyclerView.Adapter<KeywordAdapter<Any>.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = when (type) {
            "video" -> {
                inflater.inflate(R.layout.recycle_channel_video_item, parent, false)
            }
            "channel" -> {
                inflater.inflate(R.layout.recycle_mychannel_item, parent, false)
            }
            "playlist" -> {
                inflater.inflate(R.layout.recycle_playlist_item, parent, false)
            }
            else -> {
                inflater.inflate(R.layout.recycle_keyword_item, parent, false)
            }
        }

        return ViewHolder(contactView!!, type)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]
        when (type) {
            "video" -> {
                if (data is VideoFilter) {
                    if (!data.title.isNullOrEmpty()) {
                        holder.videoTitle.text = data.title
                    }

                    if (!data.duration.isNullOrEmpty() && data.duration.contains(":")) {
                        val array = data.duration.split(":")
                        if (array[0].equals("00") && array.size == 3) {
                            holder.duration.text = array[1].plus(":").plus(array[2])
                        } else {
                            holder.duration.text = data.duration
                        }
                    } else {
                        holder.duration.text = data.duration
                    }
                    var date = data.createdAt
                    var count = data.viewsCount
                    if (!data.createdAt.isNullOrEmpty()) {
                        holder.txtsubscriber.text = "${data.createdAt} . ${data.viewsCount} views"
                    }
                    if (!data.thumbnailUrl.isNullOrEmpty()) {
                        imageLoader.displayImage(data.thumbnailUrl, holder.thumbnail)
                    }
                }
            }
            "channel" -> {
                if (data is ChannelFilter) {
                    if (!data.channelName.isNullOrEmpty()) {
                        holder.channelName.text = data.channelName
                    }
                    if (data.subscribers != null) {
                        holder.txtsubscriber.text = "${data.subscribers} Subscribers"
                    }
//                    if (data.noOfVideos != null) {
//                        holder.txtVideo.text = "${data.noOfVideos} Videos"
//                    }
                    if (data.coverImage.isNullOrEmpty()) {
                        imageLoader.displayImage(
                            "drawable://" + R.drawable.circle_user,
                            holder.channelImage,
                            roundProfilePic()
                        )
                    } else {
                        imageLoader.displayImage(
                            data.coverImage,
                            holder.channelImage,
                            roundProfilePic()
                        )
                    }
                    holder.moreIcon.visibility = View.GONE
                }
            }

            "playlist" -> {
                if (data is PlaylistFilter) {
                    holder.videoTitle.text = data.playlistName
                    holder.txtDesc.text = data.description
//                    holder.numVid.text = data.noOfVideos.toString()
                    if (data.playlistIcon.isNotEmpty()) {
                        imageLoader.displayImage(data.playlistIcon, holder.thumbnail)
                    }
                    holder.moreBtn.visibility = View.GONE
                }
            }

            else -> {
                if (data is KeywordBean) {
                    holder.keyword.text = data.keyword
                    holder.keywordLay.setOnClickListener {
                        if (context is SearchActivity){
                            context.editSearch.setText(data.keyword)
                            context.search(context.upload, context.type)
                        }
                    }
                }
            }
        }
    }

    inner class ViewHolder(row: View, type: String) : RecyclerView.ViewHolder(row),
        View.OnClickListener {

        val thumbnail = row.thumbnail                           //vid
        val duration = row.duration
        val videoTitle = row.videoTitle
        val btnMore = row.btnMore
        val txtsubscriber = row.txtsubscriber

        val channelImage = row.channelImage
        val channelName = row.channelName

        //        val txtsubscriber = row.txtsubscriber
        val txtVideo = row.txtVideo
        val moreIcon = row.moreIcon

        //        val thumbnail = row.thumbnail
//        val videoTitle = row.videoTitle
        val txtDesc = row.txtDesc
        val numVid = row.numVid
        val moreBtn = row.btnMore

        val keyword = row.keyword
        val keywordLay = row.keywordLay

        init {
            row.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val data = dashList[adapterPosition]
            if (type.equals("video")) {
                val intent = Intent(context, VideoPlayerScreen::class.java)
                if (data is VideoFilter) {
                    intent.putExtra("uid", data.uid)
                }
                if (context is SearchActivity) {
                    (context).startActivityForResult(intent, CHANGE_HOME_DATA)
                } else {
                    (context as Playlistpage).startActivityForResult(intent, CHANGE_HOME_DATA)
                }
            } else if (type.equals("channel")) {
                if (data is ChannelFilter) {
                    val intent = Intent(context, ChannelPage::class.java)
                    intent.putExtra("id", data.id.toString())
                    intent.putExtra("name", data.channelName)
                    intent.putExtra("userChannel", true)
                    context.startActivity(intent)
                }
            } else {
                if (data is PlaylistFilter) {
                    val intent = Intent(context, Playlistpage::class.java)
                    intent.putExtra("id", data.id.toString())
                    intent.putExtra("name", data.playlistName)
                    intent.putExtra("isUserChannel", true)
                    context.startActivity(intent)
                }
            }
        }
    }

    fun roundProfilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true)
            .showImageOnLoading(R.drawable.circle_user)
            .displayer(RoundedBitmapDisplayer(200)).build()
    }

    fun updateKeyList(carList: ArrayList<KeywordBean>) {
        dashList = carList as ArrayList<Any>
        notifyDataSetChanged()
    }
}