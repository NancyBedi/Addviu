package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadDataBean {
    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("message")
    @Expose
    var message = ""
}