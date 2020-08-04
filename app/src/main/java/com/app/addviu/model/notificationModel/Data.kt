package com.app.addviu.model.notificationModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("notificationCount")
    @Expose
    var notificationCount = 0

    @SerializedName("notifications")
    @Expose
    var notifications = ArrayList<Notification>()
}