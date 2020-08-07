package com.app.addviu.model.userDetailModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserData {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("contact")
    @Expose
    var contact: String = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String = ""

    @SerializedName("creator_reward")
    @Expose
    var creatorReward = 0

    @SerializedName("viewer_reward")
    @Expose
    var viewerReward = 0

    @SerializedName("avatar")
    @Expose
    var avatar: String = ""

    @SerializedName("banner")
    @Expose
    var banner: String = ""

}