package com.app.addviu.view.viewInterface

import com.app.addviu.model.videoModel.VideoUploadBean
import retrofit2.Call

interface UploadVideoRequest {

    fun getRunningRequest(call: Call<VideoUploadBean>)
}