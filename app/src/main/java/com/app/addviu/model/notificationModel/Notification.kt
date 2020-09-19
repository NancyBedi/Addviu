package com.app.addviu.model.notificationModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Notification {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("uid")
    @Expose
    var uid: String = ""

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String = ""

    @SerializedName("channel_id")
    @Expose
    var channelId = 0

    @SerializedName("channel_slug")
    @Expose
    var channelSlug: String = ""

    @SerializedName("channel_name")
    @Expose
    var channelName: String = ""

    @SerializedName("channel_image")
    @Expose
    var channelImage: String = ""

    @SerializedName("notification_id")
    @Expose
    var notificationId = 0

    @SerializedName("notification_video_id")
    @Expose
    var notificationVideoId = 0

    @SerializedName("notification_user_id")
    @Expose
    var notificationUserId = 0

    @SerializedName("notification_channel_id")
    @Expose
    var notificationChannelId = 0

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("created_date")
    @Expose
    var createdDate: String = ""
}