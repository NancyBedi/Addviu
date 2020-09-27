package com.app.addviu.model.playlistDetailBean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaylistDetailData {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("channel_id")
    @Expose
    var channelId = 0

    @SerializedName("playlist_name")
    @Expose
    var playlistName: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("playlist_icon")
    @Expose
    var playlistIcon: String? = ""

    @SerializedName("banner")
    @Expose
    var banner: String? = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""
}