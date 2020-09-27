package com.app.addviu.model.channelDetailBean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelDetailData {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("channel_name")
    @Expose
    var channelName: String = ""

    @SerializedName("slug")
    @Expose
    var slug: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("banner")
    @Expose
    var banner: String? = ""

    @SerializedName("cover_image")
    @Expose
    var coverImage: String = ""

    @SerializedName("category_id")
    @Expose
    var categoryId = 0

    @SerializedName("default_channel")
    @Expose
    var defaultChannel = 0

    @SerializedName("subscribers")
    @Expose
    var subscribers = 0

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""
}