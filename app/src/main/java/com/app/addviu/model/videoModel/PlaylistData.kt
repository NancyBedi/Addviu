package com.app.addviu.model.videoModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class PlaylistData {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0

    @SerializedName("channel_id")
    @Expose
    var channelId: Int = 0

    @SerializedName("playlist_name")
    @Expose
    var playlistName: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("playlist_icon")
    @Expose
    var playlistIcon: String = ""

    @SerializedName("banner")
    @Expose
    var banner: String = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""

    @SerializedName("no_of_videos")
    @Expose
    var noOfVideos = 0
}