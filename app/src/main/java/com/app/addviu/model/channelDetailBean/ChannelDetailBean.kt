package com.app.addviu.model.channelDetailBean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelDetailBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = ChannelDetailData()
}