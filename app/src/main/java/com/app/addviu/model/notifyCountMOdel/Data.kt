package com.app.addviu.model.notifyCountMOdel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("notificationCount")
    @Expose
    var notificationCount: Int = 0
}