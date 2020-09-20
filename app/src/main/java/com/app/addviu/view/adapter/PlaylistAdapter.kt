package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.model.videoModel.PlaylistData
import com.app.addviu.view.activity.Playlistpage
import com.app.addviu.view.fragments.ChannelPlaylist
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.recycle_playlist_item.view.*

class PlaylistAdapter(
    private val imageLoader: ImageLoader,
    private val mainList: ArrayList<PlaylistData>,
    val context: Context,
    val fragment: ChannelPlaylist,
    val isUserChannel:Boolean
) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>(), YesClick {

    private var contactView: View? = null
    var playlistId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.recycle_playlist_item, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainList[position]

        holder.videoTitle.text = data.playlistName
        holder.txtDesc.text = data.description
        holder.numVid.text = data.noOfVideos.toString()
        if (data.playlistIcon.isNotEmpty()) {
            imageLoader.displayImage(data.playlistIcon, holder.thumbnail)
        }
        if (!isUserChannel) {
            holder.moreBtn.visibility = VISIBLE
            holder.moreBtn.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(context, holder.moreBtn)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            fragment.newPlaylist(context, "edit", data)
//                        (context as MyChannels).getData(data)
                        }
                        R.id.delete -> {
                            playlistId = data.id.toString()
                            Util.showDeleteDialog(
                                context,
                                "Are you really want to remove this Playlist",
                                this
                            )
                        }
                    }
                    true
                })
                popupMenu.show()
            }
        }else{
            holder.moreBtn.visibility = GONE
        }
    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val thumbnail = row.thumbnail
        val videoTitle = row.videoTitle
        val txtDesc = row.txtDesc
        val numVid = row.numVid
        val moreBtn = row.btnMore

        init {
            row.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val data = mainList[adapterPosition]

//            if(data.title == "My Channels"){
            val intent = Intent(context, Playlistpage::class.java)
            intent.putExtra("id", data.id.toString())
            intent.putExtra("name", data.playlistName)
            intent.putExtra("isUserChannel", isUserChannel)
            context.startActivity(intent)
//            }

        }
    }

    override fun yesClick() {
        AppController.instance?.dataManager?.deletePlaylist(playlistId, fragment, context)
    }

}