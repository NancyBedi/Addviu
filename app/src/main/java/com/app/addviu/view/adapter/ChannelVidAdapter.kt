package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.CHANGE_HOME_DATA
import com.app.addviu.model.channelVideoModel.ChannelVidData
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.activity.SideMenuVid
import com.app.addviu.view.activity.VideoPlayerScreen
import com.app.addviu.view.activity.VideoUploadScreen
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.recycle_channel_video_item.view.*

class ChannelVidAdapter (private val imageLoader: ImageLoader,
                         private val mainList: ArrayList<ChannelVidData>,
                         val context: Context
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

        holder.videoTitle.text = data.title
        holder.duration.text = data.duration
        holder.txtsubscriber.text = "${data.created_date} . ${data.viewsCount} views"
        if (data.thumbnailUrl.isNotEmpty()) {
            imageLoader.displayImage(data.thumbnailUrl, holder.thumbnail)
        }
        holder.btnMore.setOnClickListener {
//            val wrapper: Context = ContextThemeWrapper(this, R.style.PopupMenu)
            val popupMenu: PopupMenu = PopupMenu(context    , holder.btnMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        val intent = Intent(context, VideoUploadScreen::class.java)
                        intent.putExtra("videoData",data)
                        context.startActivity(intent)
                    }
                    R.id.delete -> {
                        Util.showDeleteDialog(
                            context,
                            "Are you really want to delete this Video",
                            this)
                    }
                }
                true
            })
            popupMenu.show()
        }
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
            (context as ChannelPage).startActivityForResult(intent, CHANGE_HOME_DATA)
//            if(data.title == "My Channels"){
//                context.startActivity(Intent(context, MyChannels::class.java))
//            }

        }
    }

    override fun yesClick() {
//        AppController.instance?.dataManager?
    }
}