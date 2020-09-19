package com.app.addviu.model.notifyCountMOdel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotifyCountBean {
    @SerializedName("status")
    @Expose
    var status: Int = 0

    @SerializedName("data")
    @Expose
    var data = Data()
}