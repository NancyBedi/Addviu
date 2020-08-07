package com.app.addviu.model.subscribedChannel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubscribedChannelBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("recommendedChannels")
    @Expose
    var recommendedChannels = SuggestSubData()

    @SerializedName("subscribedChannels")
    @Expose
    var data = ArrayList<SubscribedChannelData>()
}