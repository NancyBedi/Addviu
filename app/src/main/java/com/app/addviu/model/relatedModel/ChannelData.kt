package com.app.addviu.model.relatedModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelData() : Parcelable{

    @SerializedName("subscribers")
    @Expose
    var subscribers = 0

    @SerializedName("cover_image")
    @Expose
    var coverImage:String? = ""

    @SerializedName("channel_name")
    @Expose
    var channelName: String = ""

    @SerializedName("slug")
    @Expose
    var slug: String = ""

    constructor(parcel: Parcel) : this() {
        subscribers = parcel.readInt()
        coverImage = parcel.readString()?:""
        channelName = parcel.readString()?:""
        slug = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(subscribers)
        parcel.writeString(coverImage)
        parcel.writeString(channelName)
        parcel.writeString(slug)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChannelData> {
        override fun createFromParcel(parcel: Parcel): ChannelData {
            return ChannelData(parcel)
        }

        override fun newArray(size: Int): Array<ChannelData?> {
            return arrayOfNulls(size)
        }
    }

}