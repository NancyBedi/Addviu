package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubscribeBean {

    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("data")
    @Expose
    var data = SubscribeData()

}