package com.app.addviu.model.searchFilterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideoFilter {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0

    @SerializedName("channel_id")
    @Expose
    var channelId: Int = 0

    @SerializedName("playlist_id")
    @Expose
    var playlistId: Int = 0

    @SerializedName("category_id")
    @Expose
    var categoryId: Int = 0

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
    var processed: Int = 0

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
    var allowVotes: Int = 0

    @SerializedName("allow_comments")
    @Expose
    var allowComments: Int = 0

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String = ""

    @SerializedName("watch_seconds")
    @Expose
    var watchSeconds: Int = 0

    @SerializedName("quality_height")
    @Expose
    var qualityHeight: Int = 0

    @SerializedName("quality_width")
    @Expose
    var qualityWidth: Int = 0

    @SerializedName("views_count")
    @Expose
    var viewsCount: Int = 0

    @SerializedName("likes")
    @Expose
    var likes: Int = 0

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