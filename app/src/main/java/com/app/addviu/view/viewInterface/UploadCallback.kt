package com.app.addviu.view.viewInterface

import com.app.addviu.model.videoModel.VideoUploadData

interface UploadCallback {
    fun onProgressUpdate(percentage: Int)
    fun onError()
    fun onFinish(videoUploadData: VideoUploadData)
}