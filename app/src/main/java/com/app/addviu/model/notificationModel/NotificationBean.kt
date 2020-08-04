package com.app.addviu.model.notificationModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = Data()
}