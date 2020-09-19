package com.app.addviu.model.searchFilterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchFilterBean {
    @SerializedName("videos")
    @Expose
    var videos = ArrayList<VideoFilter>()

    @SerializedName("channels")
    @Expose
    var channels = ArrayList<ChannelFilter>()

    @SerializedName("playlists")
    @Expose
    var playlists = ArrayList<PlaylistFilter>()
}