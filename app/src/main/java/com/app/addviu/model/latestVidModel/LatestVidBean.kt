package com.app.addviu.model.latestVidModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LatestVidBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("message")
    @Expose
    var message: String = ""

    @SerializedName("data")
    @Expose
    var data = LatestVidData()
}