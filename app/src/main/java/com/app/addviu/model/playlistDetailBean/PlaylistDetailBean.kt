package com.app.addviu.model.playlistDetailBean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaylistDetailBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = PlaylistDetailData()
}