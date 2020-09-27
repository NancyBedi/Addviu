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
import com.app.addviu.model.notificationModel.Notification
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.VideoPlayerScreen
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.recycle_notify_item.view.*

class NotificationAdapter(
    private val imageLoader: ImageLoader,
    private val dashList: ArrayList<HomeData>,
    val context: Context
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.recycle_notify_item, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]
//                if(empty($notificationArray['comment_id']) && $notificationArray['id'] == $notificationArray['notification_video_id'] && $notificationArray['user_id'] == $notificationArray['notification_user_id']){  ** for published videos**    }
//                elseif(!empty($notificationArray['comment_id'])){     ** display comments***    }
//                else{ *** for subscribed vidoes ****}
        holder.videoTitle.text = data.title
        holder.time.text = data.createdAt

        if (!data.channelImage.isNullOrEmpty()) {
            imageLoader.displayImage(
                data.channelImage,
                holder.flameIcon,
                Util.roundProfilePic()
            )
        }
        imageLoader.displayImage(
            data.thumbnailUrl,
            holder.thumbnail,
            curvePic()
        )

    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val flameIcon = row.flameIcon
        val thumbnail = row.thumbnail
        val videoTitle = row.videoTitle
        val time = row.time

        init {
            row.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val data = dashList[adapterPosition]
//            val intent = Intent(context, VideoPlayerScreen::class.java)
//            intent.putExtra("uid", data.uid)
//            (context as HomeScreen).startActivityForResult(intent, CHANGE_HOME_DATA)
            (context as HomeScreen).initializeDraggablePanel(data)



//            if (data.id == data.notificationVideoId && data.userId == data.notificationUserId ) {
//                val intent = Intent(context, ChannelPage::class.java)
//                intent.putExtra("name", data.channelName)
//                intent.putExtra("fromNotify", true)
//                intent.putExtra("id", data.channelId.toString())
//                context.startActivity(intent)
//            }else{
//                val intent = Intent(context, ChannelPage::class.java)
//                intent.putExtra("id", data.channelId.toString())
//                intent.putExtra("name", data.channelName)
//                intent.putExtra("userChannel", true)
//                context.startActivity(intent)
//            }
        }
    }

    fun curvePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.loading)
            .displayer(RoundedBitmapDisplayer(10)).build()
    }
}