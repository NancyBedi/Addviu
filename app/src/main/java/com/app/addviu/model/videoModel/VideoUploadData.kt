package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideoUploadData {

    @SerializedName("uid")
    @Expose
    var uid = ""
    @SerializedName("original_name")
    @Expose
    var originalName = ""
    @SerializedName("duration")
    @Expose
    var duration = ""
}