package com.app.addviu.model.channelVideoModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelVidData() : Parcelable {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("channel_id")
    @Expose
    var channelId = 0

    @SerializedName("playlist_id")
    @Expose
    var playlistId = 0

    @SerializedName("category_id")
    @Expose
    var categoryId = 0

    @SerializedName("uid")
    @Expose
    var uid = ""

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("original_name")
    @Expose
    var originalName = ""

    @SerializedName("keywords")
    @Expose
    var keywords = ""

    @SerializedName("description")
    @Expose
    var description = ""

    @SerializedName("processed")
    @Expose
    var processed = 0

    @SerializedName("video_filename")
    @Expose
    var videoFilename = ""

    @SerializedName("duration")
    @Expose
    var duration = ""

    @SerializedName("visibility")
    @Expose
    var visibility = ""

    @SerializedName("allow_votes")
    @Expose
    var allowVotes = 0

    @SerializedName("allow_comments")
    @Expose
    var allowComments = 0

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl = ""

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

    @SerializedName("created_date")
    @Expose
    var created_date = ""

    @SerializedName("deleted_at")
    @Expose
    var deletedAt = ""

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt = ""

    @SerializedName("channelName")
    @Expose
    var channelName: String = ""

    @SerializedName("playlistName")
    @Expose
    var playlistName: String = ""

    @SerializedName("categoryName")
    @Expose
    var categoryName: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        userId = parcel.readInt()
        channelId = parcel.readInt()
        playlistId = parcel.readInt()
        categoryId = parcel.readInt()
        uid = parcel.readString()?:""
        title = parcel.readString()?:""
        originalName = parcel.readString()?:""
        keywords = parcel.readString()?:""
        description = parcel.readString()?:""
        processed = parcel.readInt()
        videoFilename = parcel.readString()?:""
        duration = parcel.readString()?:""
        visibility = parcel.readString()?:""
        allowVotes = parcel.readInt()
        allowComments = parcel.readInt()
        thumbnailUrl = parcel.readString()?:""
        watchSeconds = parcel.readInt()
        qualityHeight = parcel.readInt()
        qualityWidth = parcel.readInt()
        viewsCount = parcel.readInt()
        likes = parcel.readInt()
        deletedAt = parcel.readString()?:""
        createdAt = parcel.readString()?:""
        created_date = parcel.readString()?:""
        updatedAt = parcel.readString()?:""
        channelName = parcel.readString()?:""
        playlistName = parcel.readString()?:""
        categoryName = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeInt(channelId)
        parcel.writeInt(playlistId)
        parcel.writeInt(categoryId)
        parcel.writeString(uid)
        parcel.writeString(title)
        parcel.writeString(originalName)
        parcel.writeString(keywords)
        parcel.writeString(description)
        parcel.writeInt(processed)
        parcel.writeString(videoFilename)
        parcel.writeString(duration)
        parcel.writeString(visibility)
        parcel.writeInt(allowVotes)
        parcel.writeInt(allowComments)
        parcel.writeString(thumbnailUrl)
        parcel.writeInt(watchSeconds)
        parcel.writeInt(qualityHeight)
        parcel.writeInt(qualityWidth)
        parcel.writeInt(viewsCount)
        parcel.writeInt(likes)
        parcel.writeString(deletedAt)
        parcel.writeString(createdAt)
        parcel.writeString(created_date)
        parcel.writeString(updatedAt)
        parcel.writeString(channelName)
        parcel.writeString(playlistName)
        parcel.writeString(categoryName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChannelVidData> {
        override fun createFromParcel(parcel: Parcel): ChannelVidData {
            return ChannelVidData(parcel)
        }

        override fun newArray(size: Int): Array<ChannelVidData?> {
            return arrayOfNulls(size)
        }
    }

}