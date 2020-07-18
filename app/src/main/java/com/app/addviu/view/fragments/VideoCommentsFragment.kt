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
import com.app.addviu.data.helper.COMMENT_ADDED
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.REPLY_ADDED
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.homeModel.AccountData
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.relatedModel.CommentsBean
import com.app.addviu.model.relatedModel.CommentsData
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.adapter.AccountListAdapter
import com.app.addviu.view.adapter.CommentsAdapter
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.account_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.recyclerView
import kotlinx.android.synthetic.main.video_comments_fragment.*
import kotlinx.android.synthetic.main.video_player_layout.*

class VideoCommentsFragment : BaseFragment(), ResponseCallback, View.OnClickListener {

    private var data: RelatedVideo? = null
    private var commentsList = ArrayList<CommentsData>()
    private var commentsAdapter: CommentsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        data = arguments?.getParcelable("data")
        getRelatedComments()
        return inflater.inflate(R.layout.video_comments_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        commentsAdapter =
            CommentsAdapter(
                imageLoader, commentsList, context!!, "comments",
                data?.uid ?: "", sharedPrefsHelper!!, this
            )
        recyclerView.adapter = commentsAdapter


    }

    fun getRelatedComments() {
        AppController.instance?.dataManager?.getRelatedComments(
            data?.uid ?: "",
            this,
            context
        )
    }

    fun setOnClickListeners() {
        closeImage.setOnClickListener(this)
        sendBtn.setOnClickListener(this)
    }

    override fun <T> success(t: T) {
        if (t is CommentsBean) {
            setDataInList(t)
        } else if (t is SignUpBean) {
            if (t.status == 1) {
                commentEditText.setText("")
                sharedPrefsHelper?.put(COMMENT_ADDED, true)
                getRelatedComments()
            } else {
                Util.showToast(t.message, context!!)
            }
        }


    }

    fun setDataInList(data: CommentsBean) {
        commentsCount.text = getString(R.string.comments).plus(" ").plus(data.commentCount.toString())
        commentsList.clear()
        commentsList.addAll(data.data)
        commentsAdapter?.notifyDataSetChanged()
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
                fragmentTransaction?.commit()


            }
            R.id.sendBtn -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    if (commentEditText.text.isNullOrBlank()) {
                        commentEditText.error = "Please enter text."
                        return
                    }
                    val map = HashMap<String, String>()
                    map["body"] = commentEditText.text.toString()
                    AppController.instance?.dataManager?.createComment(
                        data?.uid ?: "",
                        map, this, context!!
                    )
                } else {
                    context?.startActivity(
                        Intent(
                            context,
                            SignInScreen::class.java
                        )
                    )
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (sharedPrefsHelper?.get(REPLY_ADDED, false)!!) {
                sharedPrefsHelper?.put(REPLY_ADDED, false)
                getRelatedComments()
            }
        }
    }


}