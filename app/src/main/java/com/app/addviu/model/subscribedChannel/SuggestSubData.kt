package com.app.addviu.model.subscribedChannel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SuggestSubData {
    @SerializedName("comedyChannels")
    @Expose
    var comedyChannels = ArrayList<SubscribedChannelData>()

    @SerializedName("musicChannels")
    @Expose
    var musicChannels = ArrayList<SubscribedChannelData>()

    @SerializedName("sportsChannels")
    @Expose
    var sportsChannels = ArrayList<SubscribedChannelData>()

    @SerializedName("newsChannels")
    @Expose
    var newsChannels = ArrayList<SubscribedChannelData>()
}