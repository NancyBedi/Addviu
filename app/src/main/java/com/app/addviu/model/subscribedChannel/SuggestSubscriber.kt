package com.app.addviu.model.subscribedChannel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SuggestSubscriber {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = SuggestSubData()
}