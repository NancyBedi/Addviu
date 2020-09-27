package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.model.subscribedChannel.SubscribedChannelData
import com.app.addviu.model.videoModel.ChannelData
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.fragments.SubscribeChannelFragment
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.recycle_mychannel_item.view.*

class SubscribedChannelAdapter(
    private val imageLoader: ImageLoader,
    private val mainList: ArrayList<SubscribedChannelData>,
    val activity: SubscribeChannelFragment,
    val context: Context
) :
    RecyclerView.Adapter<SubscribedChannelAdapter.ViewHolder>() {

    public var channelData = ChannelData()
    private var contactView: View? = null
    var channelId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.recycle_mychannel_item, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainList[position]
        if (!data.channelName.isNullOrEmpty()) {
            holder.channelName.text = data.channelName
        }
        if (data.subscribers != null) {
            holder.txtsubscriber.text = "${data.subscribers} Subscribers"
        }
        if (data.noOfVideos != null) {
            holder.txtVideo.text = "${data.noOfVideos} Videos"
        }
        if (data.coverImage.isNullOrEmpty()) {
            imageLoader.displayImage(
                "drawable://" + R.drawable.circle_user,
                holder.channelImage,
                Util.roundProfilePic()
            )
        } else {
            imageLoader.displayImage(data.coverImage, holder.channelImage, Util.roundProfilePic())
        }
        holder.moreIcon.visibility = GONE

    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val channelImage = row.channelImage
        val channelName = row.channelName
        val txtsubscriber = row.txtsubscriber
        val txtVideo = row.txtVideo
        val moreIcon = row.moreIcon

        init {
            row.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val data = mainList[adapterPosition]
            openChannel(data)
        }
    }

    fun openChannel(data: SubscribedChannelData) {
        val intent = Intent(context, ChannelPage::class.java)
        intent.putExtra("id", data.id.toString())
        intent.putExtra("name", data.channelName)
        intent.putExtra("userChannel", true)
        context.startActivity(intent)
    }

}