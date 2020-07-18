package com.app.addviu.model.relatedModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommentsData() : Parcelable{
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0

    @SerializedName("body")
    @Expose
    var body: String = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("created_at_human")
    @Expose
    var createdAtHuman: String = ""

    @SerializedName("channel")
    @Expose
    var channel = ChannelData()

    @SerializedName("replyCount")
    @Expose
    var replyCount = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        userId = parcel.readInt()
        body = parcel.readString()?:""
        createdAt = parcel.readString()?:""
        createdAtHuman = parcel.readString()?:""
        channel = parcel.readParcelable(ChannelData::class.java.classLoader)!!
        replyCount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeString(body)
        parcel.writeString(createdAt)
        parcel.writeString(createdAtHuman)
        parcel.writeParcelable(channel, flags)
        parcel.writeInt(replyCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentsData> {
        override fun createFromParcel(parcel: Parcel): CommentsData {
            return CommentsData(parcel)
        }

        override fun newArray(size: Int): Array<CommentsData?> {
            return arrayOfNulls(size)
        }
    }


}