package com.app.addviu.model.subscribedChannel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class SubscribedChannelBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<SubscribedChannelData>()
}