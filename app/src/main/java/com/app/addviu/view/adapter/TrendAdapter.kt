package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.home_list_adapter.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class TrendAdapter(
    private val imageLoader: ImageLoader,
    private var dashList: ArrayList<HomeData>,
    val context: Context,
    public val baseFragment: BaseFragment
) :
    RecyclerView.Adapter<TrendAdapter.ViewHolder>() {

    private var contactView: View? = null
    private val ITEM = 0
    private val LOADING = 1
    private val HERO = 2

    private var isLoadingAdded = false
    private val retryPageLoad = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            ITEM -> {
                contactView = inflater.inflate(R.layout.home_list_adapter, parent, false)

//                val viewItem =
//                    inflater.inflate(R.layout.item_list, parent, false)
//                viewHolder = ViewHolder(contactView!!)
            }
            LOADING -> {
                contactView = inflater.inflate(R.layout.item_progress, parent, false)

//                val viewLoading =
//                    inflater.inflate(R.layout.item_progress, parent, false)
//                viewHolder = LoadingVH(contactView!!)
            }
        }
//        return ViewHolder(contactView!!)
        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]

        when (getItemViewType(position)) {
            ITEM -> {
                holder.textTitle.text = data.title

                holder.detailText.text =
                    data.channelName.plus(" . ").plus(data.viewsCount.toString()).plus(" views")
                        .plus(" . ").plus(data.createdDate)

                if (data.duration.isNotBlank() && data.duration.contains(":")) {
                    val array = data.duration.split(":")
                    if (array[0].equals("00") && array.size == 3) {
                        holder.textTime.text = array[1].plus(":").plus(array[2])
                    } else {
                        holder.textTime.text = data.duration
                    }
                } else {
                    holder.textTime.text = data.duration
                }

                if (!data.thumbnailUrl.isNullOrEmpty()) {
                    imageLoader.displayImage(
                        data.thumbnailUrl,
                        holder.bannerImage,
                        Util.profilePic()
                    )
                }

                if (data.channelImage.isNullOrBlank()) {
                    holder.userImage.setImageResource(R.drawable.circle_user)
                } else {
                    imageLoader.displayImage(
                        data.channelImage,
                        holder.userImage,
                        Util.roundProfilePic()
                    )
                }

                holder.userImage.setOnClickListener {
                    openChannel(data)
                }
                holder.detailText.setOnClickListener {
                    openChannel(data)
                }
            }
            LOADING -> {
                if (retryPageLoad) {
                    holder.mErrorLayout.visibility = View.VISIBLE
                    holder.mProgressBar.visibility = View.GONE
//                    holder.mErrorTxt.text =
//                        if (errorMsg != null) errorMsg else context.getString(R.string.error_msg_unknown)
                } else {
                    holder.mErrorLayout.visibility = View.GONE
                    holder.mProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }


//        holder.textTitle.text = data.title
//
//        holder.detailText.text =
//            data.channelName.plus(" . ").plus(data.viewsCount.toString()).plus(" views").
//            plus(" . ").plus(data.createdDate)
//
//        if(data.duration.isNotBlank() && data.duration.contains(":")) {
//            val array = data.duration.split(":")
//            if (array[0].equals("00") && array.size == 3) {
//                holder.textTime.text = array[1].plus(":").plus(array[2])
//            }else {
//                holder.textTime.text = data.duration
//            }
//        }else{
//            holder.textTime.text = data.duration
//        }
//
//        imageLoader.displayImage(
//            data.thumbnailUrl,
//            holder.bannerImage,
//            (context as HomeScreen).profilePic()
//        )
//
//        if(data.channelImage.isNullOrBlank()){
//            holder.userImage.setImageResource(R.drawable.circle_user)
//        }else {
//            imageLoader.displayImage(data.channelImage, holder.userImage, context.roundProfilePic())
//        }
//
//        holder.userImage.setOnClickListener {
//            openChannel(data)
//        }
//        holder.detailText.setOnClickListener {
//            openChannel(data)
//        }

    fun addLoadingFooter() {
        isLoadingAdded = true
        notifyItemInserted(dashList.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = dashList.size - 1
//        val result: HomeData = get(position)
//        if (result != null) {
            dashList.removeAt(position)
            notifyItemRemoved(position)
//        }
    }

    override fun getItemViewType(position: Int): Int {
//        return if (position == 0) {
//            HERO
//        } else {
           return if (position == dashList.size - 1 && isLoadingAdded) LOADING else ITEM
//        }
    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val textTitle = row.textTitle
        val detailText = row.detailText
        val userImage = row.userImage
        val moreIcon = row.moreIcon
        val bannerImage = row.bannerImage
        val textTime = row.textTime

         val mProgressBar = row.loadmore_progress
         val mRetryBtn = row.loadmore_retry
         val mErrorTxt = row.loadmore_errortxt
         val mErrorLayout = row.loadmore_errorlayout


        init {
            row.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            if(baseFragment is HomeFragment){
                baseFragment.selectedPosition = adapterPosition
//            }else if(baseFragment is TrendPagination){
            }else if(baseFragment is TrendingFragment){
                baseFragment.selectedPosition = adapterPosition
            }
            val data = dashList[adapterPosition]
            val intent = Intent(context, VideoPlayerScreen::class.java)
            intent.putExtra("uid", data.uid)
            (context as HomeScreen).startActivityForResult(intent, CHANGE_HOME_DATA)
        }
    }

    protected class LoadingVH(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val mProgressBar: ProgressBar
        private val mRetryBtn: ImageButton
        private val mErrorTxt: TextView
        private val mErrorLayout: LinearLayout
        override fun onClick(view: View) {
            when (view.id) {
                R.id.loadmore_retry, R.id.loadmore_errorlayout -> {
//                    showRetry(false, null)
//                    baseFragment.retryPageLoad()
                }
            }
        }

        init {
            mProgressBar = itemView.findViewById(R.id.loadmore_progress)
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry)
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt)
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout)
            mRetryBtn.setOnClickListener(this)
            mErrorLayout.setOnClickListener(this)
        }
    }


    fun openChannel(data: HomeData){
        val intent = Intent(context, ChannelPage::class.java)
        intent.putExtra("id", data.channelId.toString())
        intent.putExtra("name", data.channelName)
        intent.putExtra("userChannel", true)
        context.startActivity(intent)
    }

    fun updateList(homeList: ArrayList<HomeData>) {
        dashList = homeList
        notifyDataSetChanged()
    }

}