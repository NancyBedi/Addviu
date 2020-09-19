package com.app.addviu.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonSuccess {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("message")
    @Expose
    var message: String = ""
}