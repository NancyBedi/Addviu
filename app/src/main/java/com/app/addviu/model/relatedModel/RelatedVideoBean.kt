package com.app.addviu.model.relatedModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedVideoBean {

    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("message")
    @Expose
    var message = ""
    @SerializedName("data")
    @Expose
    var data = RelatedVideoData()
}