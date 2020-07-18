package com.app.addviu.model.userModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpBean {

    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("message")
    @Expose
    var message = ""

}