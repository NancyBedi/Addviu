package com.app.addviu.model.relatedModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommentsBean() : Parcelable{
    @SerializedName("status")
    @Expose
    var status: Int = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<CommentsData>()

    @SerializedName("commentCount")
    @Expose
    var commentCount: Int = 0

    constructor(parcel: Parcel) : this() {
        status = parcel.readInt()
        commentCount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeInt(commentCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentsBean> {
        override fun createFromParcel(parcel: Parcel): CommentsBean {
            return CommentsBean(parcel)
        }

        override fun newArray(size: Int): Array<CommentsBean?> {
            return arrayOfNulls(size)
        }
    }
}