package com.app.addviu.model.subscribedChannel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MusicChannel {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("channel_name")
    @Expose
    var channelName: String = ""

    @SerializedName("slug")
    @Expose
    var slug: String = ""

    @SerializedName("cover_image")
    @Expose
    var coverImage: String = ""

    @SerializedName("subscribers")
    @Expose
    var subscribers = 0

    @SerializedName("no_of_videos")
    @Expose
    var noOfVideos = 0
}