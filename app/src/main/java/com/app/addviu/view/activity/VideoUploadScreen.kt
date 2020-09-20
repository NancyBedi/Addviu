package com.app.addviu.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.addviu.R
import com.app.addviu.data.helper.*
import com.app.addviu.model.channelVideoModel.ChannelVidData
import com.app.addviu.model.videoModel.*
import com.app.addviu.presenter.VideoUploadPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.searchDailog.OnSearchItemSelected
import com.app.addviu.view.searchDailog.SearchListItem
import com.app.addviu.view.searchDailog.SearchableDialog
import com.app.addviu.view.viewInterface.UploadCallback
import com.app.addviu.view.viewInterface.UploadVideoRequest
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_video_upload_screen.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class VideoUploadScreen : BaseActivity(), UploadCallback, View.OnClickListener, UploadVideoRequest {

    private var uploadPresenter: VideoUploadPresenter? = null
    private var readPermission = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private var viewClicked = ""
    var runningRequest: Call<VideoUploadBean>? = null
    var categoryList = ArrayList<CategoryData>()
    var channelList = ArrayList<ChannelData>()
    var playList = ArrayList<PlaylistData>()
    var categoryId = 0
    var channelId = 0
    var playlistId = 0
    var commentsValue = 1
    var likeDislikeVal = 1
    var uriImage: Uri? = null
    var videoUploadFinished = false
    var dataUploadFinished = false
    var backPressed = false
    var channelVidData  = ChannelVidData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_upload_screen)

        Util.fullScreen(this)
        if (intent != null && intent.hasExtra("videoData")) {
            channelVidData = intent.getParcelableExtra("videoData")!!
        }

        if (intent.hasExtra("videoData")) {
            uploadIcon.visibility = GONE
            uploadText.text = "Edit Video"
            titleEditText.setText(channelVidData.title)
            keywordEditText.setText(channelVidData.keywords)
            descriptionEditText.setText(channelVidData.description)
            categoryId = channelVidData.categoryId
            channelId = channelVidData.channelId
            playlistId = channelVidData.playlistId
            categoryEditText.setText(channelVidData.categoryName)
            channelEditText.setText(channelVidData.channelName)
            playlistEditText.setText(channelVidData.playlistName)
            statusEditText.setText(channelVidData.visibility)
            imageLoader.displayImage(channelVidData.thumbnailUrl, thumbImage, profilePic())
            imageIcon.visibility = GONE
            btnPublish.isEnabled = true
            btnPublish.text = "Save"
        } else {
            uploadIcon.visibility = VISIBLE
            uploadText.text = "Upload Video"
        }
        initViews()
    }

    private fun initViews() {
        uploadPresenter = VideoUploadPresenter(this)
        val layoutParams = backImage.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = statusBarHeight
        addThumbText.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setClickListeners()
    }

    private fun setClickListeners() {
        uploadVideoLayout.setOnClickListener(this)
        uploadIcon.setOnClickListener(this)
        uploadText.setOnClickListener(this)
        backImage.setOnClickListener(this)
        addThumbText.setOnClickListener(this)
        cancelUploadText.setOnClickListener(this)
        statusEditText.setOnClickListener(this)
        categoryEditText.setOnClickListener(this)
        channelEditText.setOnClickListener(this)
        playlistEditText.setOnClickListener(this)
        btnPublish.setOnClickListener(this)

        commentCheckBox.setOnCheckedChangeListener { _, isChecked ->
            commentsValue = if (isChecked) {
                1
            } else {
                0
            }
        }

        likeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            likeDislikeVal = if (isChecked) {
                1
            } else {
                0
            }
        }
    }

    override fun onFinish(videoUploadData: VideoUploadData) {
        videoUploadFinished = true
        onClickCancelUpload()
        afterVideoUploaded()
    }

    private fun afterVideoUploaded() {
        uploadIcon.setImageResource(R.drawable.ic_close_white_24dp)
        uploadText.text = getString(R.string.remove_video)
        btnPublish.isEnabled = true
        btnPublish.alpha = 1.0f
    }

    override fun onProgressUpdate(percentage: Int) {
        progressBar.progress = percentage.toFloat()
        progressBar.setText(percentage.toString())
    }

    override fun onError() {
        onClickCancelUpload()
    }

    private fun onClickCancelUpload() {
        progressBar.visibility = GONE
        cancelUploadText.visibility = GONE
        uploadVideoLayout.visibility = VISIBLE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (viewClicked == "video") {
                openGalleryForVideo()
            } else if (viewClicked == "image") {
                openGalleryForImage()
            }
        }
    }

    fun afterDeleteVideo() {
        uploadIcon.setImageResource(R.drawable.ic_file_upload_white_24dp)
        uploadText.text = getString(R.string.upload_video)
        btnPublish.isEnabled = false
        btnPublish.alpha = 0.5f
        videoUploadFinished = false
        if (backPressed) {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.uploadVideoLayout, R.id.uploadIcon, R.id.uploadText -> {
                if (uploadText.text.toString() == getString(R.string.upload_video)) {
                    viewClicked = "video"
                    if (hasPermissions(this, readPermission)) {
                        openGalleryForVideo()
                    } else {
                        requestPermissionsSafely(readPermission, READ_PERMISSION_CODE, this)
                    }
                } else {
                    uploadPresenter?.deleteVideo()
                }
            }
            R.id.backImage -> {
                onBackPressed()
            }
            R.id.addThumbText -> {
                viewClicked = "image"
                if (hasPermissions(this, readPermission)) {
                    openGalleryForImage()
                } else {
                    requestPermissionsSafely(readPermission, READ_PERMISSION_CODE, this)
                }
            }
            R.id.cancelUploadText -> {
                runningRequest?.cancel()
                onClickCancelUpload()
            }
            R.id.statusEditText -> {
                val arrayList = ArrayList<SearchListItem>()
                arrayList.add(SearchListItem(0, "Public"))
                arrayList.add(SearchListItem(1, "Private"))

                val searchableDialog = SearchableDialog(
                    this, arrayList, "Select Status",
                    "", 0
                )
                searchableDialog.show()

                searchableDialog.setOnItemSelected(object : OnSearchItemSelected {
                    override fun onClick(position: Int, searchListItem: SearchListItem?) {
                        statusEditText.setText(searchListItem?.title)
                        searchableDialog.dismiss()
                    }

                })
            }
            R.id.categoryEditText -> {
                if (categoryList.size == 0) {
                    uploadPresenter?.getCategories()
                } else {
                    showCategoryDialog(categoryList)
                }
            }
            R.id.channelEditText -> {
                if (channelList.size == 0) {
                    uploadPresenter?.getUserChannels()
                } else {
                    showChannelDialog(channelList)
                }
            }
            R.id.playlistEditText -> {
                if (channelEditText.text.toString().isBlank()) {
                    Util.showToast("Please select Channel first.", this)
                    return
                }
                if (playList.size == 0) {
                    uploadPresenter?.getChannelPlaylists(channelId.toString())
                } else {
                    showPlaylistDialog(playList)
                }
            }
            R.id.btnPublish -> {
                if (intent.hasExtra("videoData")) {

                }else{
                    if (checkValidation()) {
                        val builder = MultipartBody.Builder()
                        builder.setType(MultipartBody.FORM)
                        if (uriImage?.path != null) {
                            val file = File(RealPathUtil.getRealPath(this, uriImage))
                            builder.addFormDataPart(
                                "thumbnail",
                                file.name,
                                file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                            )
                        } else {
                            builder.addFormDataPart("thumbnail", "")
                        }
                        builder.addFormDataPart("title", titleEditText.text.toString())
                        builder.addFormDataPart("keywords", keywordEditText.text.toString())
                        builder.addFormDataPart("channel_id", channelId.toString())
                        builder.addFormDataPart("description", descriptionEditText.text.toString())
                        builder.addFormDataPart(
                            "visibility",
                            statusEditText.text.toString().toLowerCase(Locale.getDefault())
                        )
                        builder.addFormDataPart("uid", uploadPresenter?.videoUploadData?.uid!!)
                        builder.addFormDataPart(
                            "original_name",
                            uploadPresenter?.videoUploadData?.originalName!!
                        )
                        builder.addFormDataPart(
                            "duration",
                            uploadPresenter?.videoUploadData?.duration!!
                        )
                        builder.addFormDataPart("allow_comments", commentsValue.toString())
                        builder.addFormDataPart("allow_votes", likeDislikeVal.toString())
                        builder.addFormDataPart("playlistid", playlistId.toString())
                        builder.addFormDataPart("category_id", categoryId.toString())

                        uploadPresenter?.uploadVideoData(builder)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (videoUploadFinished && !dataUploadFinished) {
            backPressed = true
            uploadPresenter?.deleteVideo()
        } else {
            runningRequest?.cancel()
            super.onBackPressed()
        }
    }

    fun showCategoryDialog(arrayList: ArrayList<CategoryData>) {
        val searchListItems = ArrayList<SearchListItem>()
        for (i in arrayList.indices) {
            searchListItems.add(SearchListItem(arrayList[i].id, arrayList[i].name))
        }
        val searchableDialog = SearchableDialog(
            this, searchListItems, "Select Category",
            "Search Category", 1
        )
        searchableDialog.show()

        searchableDialog.setOnItemSelected(object : OnSearchItemSelected {
            override fun onClick(position: Int, searchListItem: SearchListItem?) {
                categoryEditText.setText(searchListItem?.title)
                categoryId = searchListItem?.id ?: 0
                searchableDialog.dismiss()
            }

        })
    }

    fun showChannelDialog(arrayList: ArrayList<ChannelData>) {
        val searchListItems = ArrayList<SearchListItem>()
        for (i in arrayList.indices) {
            searchListItems.add(SearchListItem(arrayList[i].id, arrayList[i].channelName))
        }
        val searchableDialog = SearchableDialog(
            this, searchListItems, "Select Channel",
            "Search Channel", 1
        )
        searchableDialog.show()

        searchableDialog.setOnItemSelected(object : OnSearchItemSelected {
            override fun onClick(position: Int, searchListItem: SearchListItem?) {
                channelEditText.setText(searchListItem?.title)
                channelId = searchListItem?.id ?: 0
                searchableDialog.dismiss()
            }

        })
    }

    fun showPlaylistDialog(arrayList: ArrayList<PlaylistData>) {
        val searchListItems = ArrayList<SearchListItem>()
        for (i in arrayList.indices) {
            searchListItems.add(SearchListItem(arrayList[i].id, arrayList[i].playlistName))
        }
        val searchableDialog = SearchableDialog(
            this, searchListItems, "Select Playlist",
            "Search Playlist", 1
        )
        searchableDialog.show()

        searchableDialog.setOnItemSelected(object : OnSearchItemSelected {
            override fun onClick(position: Int, searchListItem: SearchListItem?) {
                playlistEditText.setText(searchListItem?.title)
                playlistId = searchListItem?.id ?: 0
                searchableDialog.dismiss()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_VIDEO) {
                data?.data?.let {

                    if (RealPathUtil.getRealPath(this, it).isNullOrEmpty()) {
                        Util.showToast("Video is corrupted or not in correct format.", this)
                    } else {
                        val file = File(RealPathUtil.getRealPath(this, it))
                        val size = file.length() / 1024
                        if ((size / 1024) <= 1024) {
                            uploadPresenter?.uploadVideo(file, progressBar)
                            Handler().postDelayed({
                                progressBar.visibility = VISIBLE
                                uploadVideoLayout.visibility = GONE
                                cancelUploadText.visibility = VISIBLE
                            }, 2000)
                        } else {
                            Util.showToast(
                                "You can upload large videos on our website." +
                                        " Here is the link www.addviu.com", this
                            )
                        }
                    }
                }
            } else if (requestCode == REQUEST_GALLERY_IMAGE) {
                uriImage = data?.data
                imageLoader.displayImage(uriImage.toString(), thumbImage, profilePic())
                imageIcon.visibility = GONE
            }
        }
    }

    override fun getRunningRequest(call: Call<VideoUploadBean>) {
        runningRequest = call
    }

    private fun checkValidation(): Boolean {

        if (Util.checkEmpty(titleEditText, "Title", this)) {
            return false
        }

        if (Util.checkEmpty(keywordEditText, "Keywords", this)) {
            return false
        }

        if (Util.checkEmpty(channelEditText, "Channel", this)) {
            return false
        }

        if (Util.checkEmpty(statusEditText, "Status", this)) {
            return false
        }

        return true
    }

}
