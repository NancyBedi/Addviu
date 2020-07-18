package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaylistBean {
    @SerializedName("status")
    @Expose
    var status: Int = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<PlaylistData>()
}