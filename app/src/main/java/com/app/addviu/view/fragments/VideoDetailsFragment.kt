package com.app.addviu.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.COMMENT_ADDED
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.WEB_URL
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.model.relatedModel.RelatedVideo
import com.app.addviu.model.relatedModel.RelatedVideoBean
import com.app.addviu.model.relatedModel.RelatedVideoData
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.SubscribeBean
import com.app.addviu.model.videoModel.VotesBean
import com.app.addviu.view.activity.ChannelPage
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.adapter.RelatedVideoAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import kotlinx.android.synthetic.main.home_fragment_layout.recyclerView
import kotlinx.android.synthetic.main.video_detail_fragment.*

class VideoDetailsFragment : BaseFragment(), ResponseCallback, View.OnClickListener {

    private var relatedVideoAdapter: RelatedVideoAdapter? = null
    private var relatedVideoList = ArrayList<HomeData>()
    private var data: RelatedVideo? = null
    private var clickedType = ""
    private var videoUid = ""
    private var channelSlug = ""
    private var firstTime = true
    private var canSubscribe = false
    var channelId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        videoUid = arguments?.getString("uid") ?: ""
        channelSlug = arguments?.getString("slug") ?: ""
        getRelatedVideos()
        return inflater.inflate(R.layout.video_detail_fragment, container, false)
    }

    private fun getRelatedVideos() {
        AppController.instance?.dataManager?.getRelatedVideos(videoUid, this, context)
    }

    override fun onResume() {
        super.onResume()
        if (firstTime) {
            firstTime = false
        } else {
            getVotesSubscribeUser()
        }
    }

    fun getVotesSubscribeUser() {
        if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
            getVideoVotes()
            getSubscribeUser()
        }
    }

    private fun getVideoVotes() {
        AppController.instance?.dataManager?.getVideoVotes(
            videoUid,
            this,
            null
        )
    }

    fun getSubscribeUser() {
        AppController.instance?.dataManager?.getSubscribeUser(
            channelSlug,
            this,
            null
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()
        relatedVideoAdapter = RelatedVideoAdapter(imageLoader, relatedVideoList, activity!!)
        recyclerView.adapter = relatedVideoAdapter

    }

    fun setOnClickListener() {
        commentsLayout.setOnClickListener(this)
        subscribeBtn.setOnClickListener(this)
        likeButton.setOnClickListener(this)
        dislikeButton.setOnClickListener(this)
        shareButton.setOnClickListener(this)
        channelName.setOnClickListener(this)
        textSubscriber.setOnClickListener(this)
        channelImage.setOnClickListener(this)
    }

    fun setData(relatedVideo: RelatedVideo) {
        data = relatedVideo
        channelSlug = relatedVideo.channel.slug
        getVotesSubscribeUser()
//        (activity as VideoPlayerScreen).vimeoExtraction(data?.videoFilename ?: "")
//        (activity as VideoPlayerScreen).relatedVideo = data
        videoTitle.text = relatedVideo.title
        detailText.text =
            relatedVideo.viewsCount.toString().plus(" views")
                .plus(" . ").plus(relatedVideo.createdDate)
        channelName.text = relatedVideo.channel.channelName
        channelId = relatedVideo.channel.id

        likeButton.text = relatedVideo.likes.toString()
    }

    fun setDataInList(data: RelatedVideoData) {
        setData(data.video)
        textComments.text =
            getString(R.string.comments).plus(" ").plus(data.commentCount.toString())
        relatedVideoList.clear()
        relatedVideoList.addAll(data.relatedVideos)
        relatedVideoAdapter?.notifyDataSetChanged()
        if(data.video.channel.coverImage.isNullOrBlank()){
            channelImage.setImageResource(R.drawable.circle_user)
        }else {
            imageLoader.displayImage(
                data.video.channel.coverImage,
                channelImage,
                (activity!! as HomeScreen).roundProfilePic()
            )
        }
    }

    override fun <T> success(t: T) {
        if (t is RelatedVideoBean) {
            if (t.status == 1) {
                setDataInList(t.data)
//                (activity as HomeScreen).setVideoPlayerVisible()
            }
        } else if (t is SignUpBean) {
            if (t.status == 1) {
                if (clickedType == "subscribe") {
                    getSubscribeUser()
                } else if (clickedType == "vote") {
                    getVideoVotes()
                }
            }
        } else if (t is SubscribeBean) {
            if (t.status == 1) {
                textSubscriber.text = t.data.count.toString().plus(" subscribers")
                canSubscribe = t.data.canSubscribe
                if (!t.data.canSubscribe) {
//                    subscribeBtn.visibility = GONE
                    subscribeBtn.setTextColor(ContextCompat.getColor(activity!!, R.color.dark_gray))
                    subscribeBtn.isEnabled = false
                } else {
                    subscribeBtn.visibility = VISIBLE
                    if (t.data.userSubscribed) {
                        subscribeBtn.text = getString(R.string.UNSUBSCRIBE)
                    } else {
                        subscribeBtn.text = getString(R.string.SUBSCRIBE)
                    }
                }
            }
        } else if (t is VotesBean) {
            if (t.status == 1) {
                dislikeButton.text = t.data.down.toString()
                likeButton.text = t.data.up.toString()
                when {
                    t.data.userVote.equals("up", ignoreCase = true) -> {
                        setVotesImages(
                            R.drawable.like_purpule,
                            R.drawable.dislike_grey
                        )
                    }
                    t.data.userVote.equals("down", ignoreCase = true) -> {
                        setVotesImages(
                            R.drawable.like_grey,
                            R.drawable.dislike_purpule
                        )
                    }
                    else -> {
                        setVotesImages(
                            R.drawable.like_grey,
                            R.drawable.dislike_grey
                        )
                    }
                }
            }
        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (sharedPrefsHelper?.get(COMMENT_ADDED, false)!!) {
                sharedPrefsHelper?.put(COMMENT_ADDED, false)
                getRelatedVideos()
            }
        }
    }

    fun setVotesImages(likeImage: Int, dislikeImage: Int) {
        dislikeButton.setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(context!!, dislikeImage), null, null
        )
        likeButton.setCompoundDrawablesWithIntrinsicBounds(
            null,
            ContextCompat.getDrawable(context!!, likeImage), null, null
        )
    }

    override fun failure(t: Throwable) {
    }

    override fun onClick(v: View?) {
        when (v?.id!!) {
            R.id.commentsLayout -> {
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                val bundle = Bundle()
                bundle.putParcelable("data", data)
                val videoCommentsFragment = VideoCommentsFragment()
                videoCommentsFragment.arguments = bundle
                fragmentTransaction?.hide(fragmentManager.findFragmentByTag("detailsFragment")!!)
                fragmentTransaction?.add(
                    R.id.frameBottom,
                    videoCommentsFragment,
                    "commentsFragment"
                )
                fragmentTransaction?.commit()
            }
            R.id.subscribeBtn -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    clickedType = "subscribe"
                    AppController.instance?.dataManager?.subscribeChannel(
                        data?.channel?.slug ?: "",
                        this,
                        context
                    )
                } else {
                    context?.startActivity(Intent(context, SignInScreen::class.java))
                }
            }
            R.id.likeButton -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    clickedType = "vote"
                    val map = HashMap<String, String>()
                    map["type"] = "up"
                    AppController.instance?.dataManager?.setVideoVotes(
                        data?.uid ?: "",
                        map, this, context
                    )
                } else {
                    context?.startActivity(Intent(context, SignInScreen::class.java))
                }
            }
            R.id.dislikeButton -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    clickedType = "vote"
                    val map = HashMap<String, String>()
                    map["type"] = "down"
                    AppController.instance?.dataManager?.setVideoVotes(
                        data?.uid ?: "",
                        map, this, context
                    )
                } else {
                    context?.startActivity(Intent(context, SignInScreen::class.java))
                }
            }
            R.id.shareButton -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
//                # change the type of data you need to share,
//                # for image use "image/*"
                intent.type = "text/plain"
                val link = WEB_URL.plus("videos/").plus(data?.uid)
                intent.putExtra(Intent.EXTRA_TEXT, link)
                startActivity(Intent.createChooser(intent, "Share"))
            }
            R.id.channelImage, R.id.channelName, R.id.textSubscriber ->{
                if (canSubscribe || !sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    val intent = Intent(activity, ChannelPage::class.java)
                    intent.putExtra("id", channelId.toString())
                    intent.putExtra("name", channelName.text.toString())
                    intent.putExtra("userChannel", true)
                    startActivity(intent)
                }
            }
        }
    }
}