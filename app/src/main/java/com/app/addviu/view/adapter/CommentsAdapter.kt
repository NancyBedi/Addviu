package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.data.helper.USER_ID
import com.app.addviu.model.relatedModel.CommentsData
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.activity.VideoPlayerScreen
import com.app.addviu.view.fragments.BaseFragment
import com.app.addviu.view.fragments.VideoCommentsFragment
import com.app.addviu.view.fragments.VideoRepliesFragment
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.related_comment_adapter.view.*


class CommentsAdapter(
    private val imageLoader: ImageLoader,
    private val dashList: ArrayList<CommentsData>,
    private val context: Context, private val type: String, private val uid: String,
    private val sharedPrefsHelper: SharedPrefsHelper, private val baseFragment: BaseFragment
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = if (type == "replies") {
            inflater.inflate(R.layout.related_reply_adapter, parent, false)
        }else{
            inflater.inflate(R.layout.related_comment_adapter, parent, false)
        }

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return dashList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dashList[position]

        holder.textBody.text = data.body
        holder.channelName.text = data.channel.channelName
        holder.commentCount?.text = data.replyCount.toString()

        if(data.replyCount>1) {
            holder.repliesCount?.text = data.replyCount.toString().plus(" Replies")
        }else{
            holder.repliesCount?.text = data.replyCount.toString().plus(" Reply")
        }

        if (data.channel.coverImage.isNullOrBlank()) {
            holder.channelImage.setImageResource(R.drawable.circle_user)
        } else {
            imageLoader.displayImage(
                data.channel.coverImage,
                holder.channelImage,
                (context as VideoPlayerScreen).roundProfilePic()
            )
        }

        if (data.userId == sharedPrefsHelper[USER_ID, 0]) {
            holder.deleteComment.visibility = VISIBLE
        } else {
            holder.deleteComment.visibility = GONE
        }


    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row), View.OnClickListener {

        val textBody = row.textComment
        val channelName = row.channelName
        val commentCount = row.commentCount
        val channelImage = row.channelImage
        val deleteComment = row.deleteComment
        val repliesCount = row.repliesCount

        init {
            repliesCount?.setOnClickListener(this)
            deleteComment.setOnClickListener(this)
            commentCount?.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            val data = dashList[adapterPosition]
            when (v?.id!!) {
                R.id.commentCount, R.id.repliesCount -> {
                    if (type == "comments") {
                        val fragmentManager = (context as VideoPlayerScreen).supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("commentsFragment")!!)
                        val bundle = Bundle()
                        bundle.putInt("commentId", data.id)
                        bundle.putString("uid", uid)
                        val videoRepliesFragment = VideoRepliesFragment()
                        videoRepliesFragment.arguments = bundle
                        fragmentTransaction.add(
                            R.id.detailContainer,
                            videoRepliesFragment,
                            "repliesFragment"
                        )
                        fragmentTransaction.commit()
                    }
                }
                R.id.deleteComment -> {
                    if (sharedPrefsHelper[IS_LOGIN, false]!!) {
                        if(baseFragment is VideoCommentsFragment) {
                            AppController.instance?.dataManager?.deleteComment(
                                uid,
                                data.id.toString(),
                                baseFragment,
                                context
                            )
                        }else if(baseFragment is VideoRepliesFragment){
                            AppController.instance?.dataManager?.deleteComment(
                                uid,
                                data.id.toString(),
                                baseFragment,
                                context
                            )
                        }
                    }else{
                        context.startActivity(Intent(context,SignInScreen::class.java))
                    }
                }
            }


        }
    }
}