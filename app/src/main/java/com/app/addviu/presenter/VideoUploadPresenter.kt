package com.app.addviu.presenter

import android.content.Context
import android.util.Log
import com.app.addviu.AppController
import com.app.addviu.data.helper.CircleProgressBar
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.*
import com.app.addviu.view.activity.VideoUploadScreen
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.VideoUploadInterface
import com.app.naxtre.mvvmfinal.data.helper.Util
import okhttp3.MultipartBody
import java.io.File


class VideoUploadPresenter(val context: Context) : ResponseCallback, VideoUploadInterface {


    var videoUploadData: VideoUploadData? = null

    override fun <T> success(t: T) {

        if (t is VideoUploadBean) {
            Util.showToast(t.message, context)
            Log.e("Uid", t.message)
            if (t.status == 1) {
                Log.e("Uid", t.data.uid)
                videoUploadData = t.data
                (context as VideoUploadScreen).onFinish(t.data)
            } else {
                (context as VideoUploadScreen).onError()
            }
        } else if (t is SignUpBean) {
            Util.showToast(t.message, context)
            if (t.status == 1) {
                (context as VideoUploadScreen).afterDeleteVideo()
            }
        } else if (t is CategoryBean) {
            if (t.status == 1) {
                (context as VideoUploadScreen).categoryList.clear()
                context.categoryList.addAll(t.data)
                context.showCategoryDialog(t.data)
            }
        } else if (t is ChannelBean) {
            if (t.status == 1) {
                (context as VideoUploadScreen).channelList.clear()
                context.channelList.addAll(t.data)
                context.showChannelDialog(t.data)
            }
        } else if (t is PlaylistBean) {
            if (t.status == 1) {
                (context as VideoUploadScreen).playList.clear()
                context.playList.addAll(t.data)
                context.showPlaylistDialog(t.data)
            }
        } else if (t is UploadDataBean) {
            Util.showToast(t.message, context)
            if (t.status == 1) {
                (context as VideoUploadScreen).dataUploadFinished = true
                context.onBackPressed()
            }
        }

    }

    override fun failure(t: Throwable) {
        Util.showToast(t.message.toString(), context)
    }


    override fun uploadVideo(file: File, progressBar: CircleProgressBar) {

                AppController.instance?.dataManager?.uploadVideo(
                    file, this, null,
                    context as VideoUploadScreen
                )

    }

    override fun deleteVideo() {
        AppController.instance?.dataManager?.deleteVideo(
            videoUploadData?.originalName!!,
            this,
            context
        )
    }

    override fun getCategories() {
        AppController.instance?.dataManager?.getCategories(this, context)
    }

    override fun getUserChannels() {
        AppController.instance?.dataManager?.getUserChannels(this, context)
    }

    override fun getChannelPlaylists(channelId: String) {
        AppController.instance?.dataManager?.channelPlaylists(channelId, this, context)
    }

    override fun uploadVideoData(builder: MultipartBody.Builder) {
        AppController.instance?.dataManager?.uploadVideoData(builder, this, context)
    }


}