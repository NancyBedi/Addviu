package com.app.addviu.model.relatedModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedVideo() : Parcelable{

    @SerializedName("channel")
    @Expose
    var channel = ChannelData()

    @SerializedName("views_count")
    @Expose
    var viewsCount = 0

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("uid")
    @Expose
    var uid = ""
    @SerializedName("created_date")
    @Expose
    var createdDate: String = ""
    @SerializedName("likes")
    @Expose
    var likes = 0

    @SerializedName("video_filename")
    @Expose
    var videoFilename: String = ""

    @SerializedName("duration")
    @Expose
    var duration: String = ""

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String = ""

    constructor(parcel: Parcel) : this() {
        channel = parcel.readParcelable(ChannelData::class.java.classLoader)!!
        viewsCount = parcel.readInt()
        title = parcel.readString()?:""
        uid = parcel.readString()?:""
        createdDate = parcel.readString()?:""
        likes = parcel.readInt()
        videoFilename = parcel.readString()?:""
        duration = parcel.readString()?:""
        thumbnailUrl = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(channel, flags)
        parcel.writeInt(viewsCount)
        parcel.writeString(title)
        parcel.writeString(uid)
        parcel.writeString(createdDate)
        parcel.writeInt(likes)
        parcel.writeString(videoFilename)
        parcel.writeString(duration)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RelatedVideo> {
        override fun createFromParcel(parcel: Parcel): RelatedVideo {
            return RelatedVideo(parcel)
        }

        override fun newArray(size: Int): Array<RelatedVideo?> {
            return arrayOfNulls(size)
        }
    }


}