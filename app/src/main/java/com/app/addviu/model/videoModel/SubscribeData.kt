package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SubscribeData {

    @SerializedName("user_subscribed")
    @Expose
    var userSubscribed: Boolean = false

    @SerializedName("can_subscribe")
    @Expose
    var canSubscribe: Boolean = false

    @SerializedName("count")
    @Expose
    var count: Int = 0
}