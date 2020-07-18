package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryData {

    @SerializedName("id")
    @Expose
    var id = 0
    @SerializedName("name")
    @Expose
    var name = ""
}