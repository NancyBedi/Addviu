package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class VotesData {

    @SerializedName("up")
    @Expose
    var up: Int = 0
    @SerializedName("down")
    @Expose
    var down: Int = 0
    @SerializedName("can_vote")
    @Expose
    var canVote: Boolean = false
    @SerializedName("user_vote")
    @Expose
    var userVote: String = ""
}