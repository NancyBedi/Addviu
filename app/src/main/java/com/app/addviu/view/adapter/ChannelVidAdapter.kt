package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.channelVideoModel.ChannelVidData
import com.app.addviu.model.channelVideosModel.Channel
import com.app.addviu.view.activity.*
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.*

class ChannelVidAdapter (private val imageLoader: ImageLoader,
                         private val mainList: ArrayList<ChannelVidData>,
                         val context: Context,
                         val isUserChannel:Boolean
) :
    RecyclerView.Adapter<ChannelVidAdapter.ViewHolder>(), YesClick {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.recycle_channel_video_item, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainList[position]

        if (!data.title.isNullOrEmpty()) {
            holder.videoTitle.text = data.title
        }

        if(!data.duration.isNullOrEmpty() && data.duration.contains(":")) {
            val array = data.duration.split(":")
            if (array[0].equals("00") && array.size == 3) {
                holder.duration.text = array[1].plus(":").plus(array[2])
            }else {
                holder.duration.text = data.duration
            }
        }else{
            holder.duration.text = data.duration
        }
        var date = data.created_date
        var count = data.viewsCount
        if (!data.created_date.isNullOrEmpty()){
            holder.txtsubscriber.text = "${data.created_date} . ${data.viewsCount} views"
        }
        if (!data.thumbnailUrl.isNullOrEmpty()) {
            imageLoader.displayImage(data.thumbnailUrl, holder.thumbnail, profilePic())
        }
        if (!isUserChannel) {
            holder.btnMore.visibility = VISIBLE
            holder.btnMore.setOnClickListener {
//            val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenu)
                val popupMenu: PopupMenu = PopupMenu(context, holder.btnMore)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            val intent = Intent(context, VideoUploadScreen::class.java)
                            intent.putExtra("videoData", data)
                            context.startActivity(intent)
                        }
                        R.id.delete -> {
                            Util.showDeleteDialog(
                                context,
                                "Are you really want to delete this Video",
                                this
                            )
                        }
                    }
                    true
                })
                popupMenu.show()
            }
        }else{
            holder.btnMore.visibility = GONE
        }
        holder.btnMore.visibility = GONE
    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val thumbnail = row.thumbnail
        val duration = row.duration
        val videoTitle = row.videoTitle
        val btnMore = row.btnMore
        val txtsubscriber = row.txtsubscriber

        init{
            row.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val data = mainList[adapterPosition]
            val intent = Intent(context, VideoPlayerScreen::class.java)
            intent.putExtra("uid", data.uid)
            if (context is ChannelPage) {
                (context as ChannelPage).startActivityForResult(intent, CHANGE_HOME_DATA)
            }else{
                (context as Playlistpage).startActivityForResult(intent, CHANGE_HOME_DATA)
            }
//            if(data.title == "My Channels"){
//                context.startActivity(Intent(context, MyChannels::class.java))
//            }

        }
    }

    fun getValue(value:String):String{
        if (value == null){
            return  ""
        }
        return value
    }

    override fun yesClick() {
//        AppController.instance?.dataManager?
    }

    fun profilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true)
            .showImageOnLoading(R.drawable.loading)
            .displayer(SimpleBitmapDisplayer()).build()
    }

}