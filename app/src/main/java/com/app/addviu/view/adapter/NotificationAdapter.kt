package com.app.addviu.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.model.notificationModel.Notification
import com.app.addviu.view.activity.HomeScreen
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.recycle_notify_item.view.*

class NotificationAdapter(
    private val imageLoader: ImageLoader,
    private val dashList: ArrayList<Notification>,
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

        holder.videoTitle.text = data.title
        holder.time.text = data.createdAt

        imageLoader.displayImage(
            data.thumbnailUrl,
            holder.flameIcon,
            (context as HomeScreen).roundProfilePic()
        )
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
//            (context as SideMenuVid).startActivityForResult(intent, CHANGE_HOME_DATA)
        }
    }

    fun curvePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.circle_user)
            .displayer(RoundedBitmapDisplayer(10)).build()
    }
}