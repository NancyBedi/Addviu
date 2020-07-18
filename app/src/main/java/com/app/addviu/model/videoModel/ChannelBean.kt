package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelBean {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("data")
    @Expose
    var data = ArrayList<ChannelData>()
}