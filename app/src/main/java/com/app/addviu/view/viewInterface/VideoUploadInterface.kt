package com.app.addviu.view.viewInterface

import com.app.addviu.data.helper.CircleProgressBar
import okhttp3.MultipartBody
import java.io.File

interface VideoUploadInterface {

    fun uploadVideo(file: File, progressBar: CircleProgressBar)

    fun deleteVideo()

    fun getCategories()

    fun getUserChannels()

    fun getChannelPlaylists(channelId:String)

    fun uploadVideoData(builder:MultipartBody.Builder)


}