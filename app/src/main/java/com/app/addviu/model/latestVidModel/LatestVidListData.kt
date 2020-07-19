package com.app.addviu.model.latestVidModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LatestVidListData {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("channel_id")
    @Expose
    var channelId = 0

    @SerializedName("channel_image")
    @Expose
    var channel_image = ""

    @SerializedName("playlist_id")
    @Expose
    var playlistId = 0

    @SerializedName("category_id")
    @Expose
    var categoryId = 0

    @SerializedName("uid")
    @Expose
    var uid: String = ""

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("original_name")
    @Expose
    var originalName: String = ""

    @SerializedName("keywords")
    @Expose
    var keywords: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("processed")
    @Expose
    var processed = 0

    @SerializedName("video_filename")
    @Expose
    var videoFilename: String = ""

    @SerializedName("duration")
    @Expose
    var duration: String = ""

    @SerializedName("visibility")
    @Expose
    var visibility: String = ""

    @SerializedName("allow_votes")
    @Expose
    var allowVotes = 0

    @SerializedName("allow_comments")
    @Expose
    var allowComments = 0

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String = ""

    @SerializedName("watch_seconds")
    @Expose
    var watchSeconds = 0

    @SerializedName("quality_height")
    @Expose
    var qualityHeight = 0

    @SerializedName("quality_width")
    @Expose
    var qualityWidth = 0

    @SerializedName("views_count")
    @Expose
    var viewsCount = 0

    @SerializedName("likes")
    @Expose
    var likes = 0

    @SerializedName("deleted_at")
    @Expose
    var deletedAt: String = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""

    @SerializedName("channel_slug")
    @Expose
    var channelSlug: String = ""

    @SerializedName("channel_name")
    @Expose
    var channelName: String = ""

    @SerializedName("created_date")
    @Expose
    var createdDate: String = ""
}