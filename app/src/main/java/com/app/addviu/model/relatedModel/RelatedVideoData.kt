package com.app.addviu.model.relatedModel

import android.os.Parcel
import android.os.Parcelable
import com.app.addviu.model.homeModel.HomeData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedVideoData() : Parcelable{

    @SerializedName("dislikes")
    @Expose
    var dislikes = 0
    @SerializedName("commentCount")
    @Expose
    var commentCount: Int = 0
    @SerializedName("video")
    @Expose
    var video = RelatedVideo()

    @SerializedName("relatedVideos")
    @Expose
    var relatedVideos = ArrayList<HomeData>()

    constructor(parcel: Parcel) : this() {
        dislikes = parcel.readInt()
        commentCount = parcel.readInt()
        video = parcel.readParcelable(RelatedVideo::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dislikes)
        parcel.writeInt(commentCount)
        parcel.writeParcelable(video, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RelatedVideoData> {
        override fun createFromParcel(parcel: Parcel): RelatedVideoData {
            return RelatedVideoData(parcel)
        }

        override fun newArray(size: Int): Array<RelatedVideoData?> {
            return arrayOfNulls(size)
        }
    }

}