package com.app.addviu.model.userDetailModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDetailBean {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = UserData()
}