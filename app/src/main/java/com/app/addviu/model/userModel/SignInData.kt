package com.app.addviu.model.userModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignInData {

    @SerializedName("token")
    @Expose
    var token = ""
    @SerializedName("id")
    @Expose
    var id = 0
    @SerializedName("name")
    @Expose
    var name = ""
}