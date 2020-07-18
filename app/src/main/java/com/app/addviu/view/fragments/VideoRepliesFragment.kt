package com.app.addviu.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.REPLY_ADDED

import com.app.addviu.model.relatedModel.CommentsBean
import com.app.addviu.model.relatedModel.CommentsData
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.adapter.CommentsAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util

import kotlinx.android.synthetic.main.video_comments_fragment.*
import kotlinx.android.synthetic.main.video_comments_fragment.view.*

class VideoRepliesFragment : BaseFragment(), ResponseCallback, View.OnClickListener {

    private var commentsList = ArrayList<CommentsData>()
    private var commentsAdapter: CommentsAdapter? = null
    private var commentId = 0
    private var uid = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        commentId = arguments?.getInt("commentId") ?: 0
        uid = arguments?.getString("uid") ?: ""
        getRelatedReplies()
        return inflater.inflate(R.layout.video_comments_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        commentsCount.text = getString(R.string.replies).plus(" ").plus(commentsList.size.toString())
        commentEditText.hint = getString(R.string.add_public_reply)
        commentsAdapter = CommentsAdapter(imageLoader, commentsList, context!!, "replies",
            uid,sharedPrefsHelper!!,this)
        recyclerView.adapter = commentsAdapter

    }

    fun setOnClickListeners() {
        backImage.visibility = VISIBLE
        backImage.setOnClickListener(this)
        closeImage.setOnClickListener(this)
        sendBtn.setOnClickListener(this)
    }

    override fun <T> success(t: T) {
        if (t is SignUpBean) {
            if (t.status == 1) {
                commentEditText.setText("")
                sharedPrefsHelper?.put(REPLY_ADDED, true)
                getRelatedReplies()
            } else {
                Util.showToast(t.message, context!!)
            }
        } else if (t is CommentsBean) {
                setDataInList(t)
        }


    }

    fun setDataInList(data: CommentsBean) {
        commentsList.clear()
        commentsList.addAll(data.data)
        commentsAdapter?.notifyDataSetChanged()
        commentsCount.text = getString(R.string.replies).plus(" ").plus(commentsList.size.toString())
    }

    fun getRelatedReplies() {
        AppController.instance?.dataManager?.getRelatedReplies(
            commentId.toString(),
            this,
            context
        )
    }

    override fun failure(t: Throwable) {
    }

    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.closeImage -> {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.show(fragmentManager.findFragmentByTag("detailsFragment")!!)
                fragmentTransaction?.remove(fragmentManager.findFragmentByTag("commentsFragment")!!)
                fragmentTransaction?.remove(fragmentManager.findFragmentByTag("repliesFragment")!!)
                fragmentTransaction?.commit()
            }
            R.id.backImage -> {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.show(fragmentManager.findFragmentByTag("commentsFragment")!!)
                fragmentTransaction?.remove(fragmentManager.findFragmentByTag("repliesFragment")!!)
                fragmentTransaction?.commit()
            }
            R.id.sendBtn -> {
                if(sharedPrefsHelper?.get(IS_LOGIN,false)!!) {
                    if (commentEditText.text.isNullOrBlank()) {
                        commentEditText.error = "Please enter text."
                        return
                    }
                    val map = HashMap<String, String>()
                    map["body"] = commentEditText.text.toString()
                    map["reply_id"] = commentId.toString()
                    AppController.instance?.dataManager?.createComment(
                        uid, map, this, context!!
                    )
                }else{
                    context?.startActivity(
                        Intent(context,
                            SignInScreen::class.java)
                    )
                }
            }
        }
    }


}