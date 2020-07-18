package com.app.addviu.model.channelVideoModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelVideoBean() : Parcelable {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<ChannelVidData>()

    constructor(parcel: Parcel) : this() {
        status = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChannelVideoBean> {
        override fun createFromParcel(parcel: Parcel): ChannelVideoBean {
            return ChannelVideoBean(parcel)
        }

        override fun newArray(size: Int): Array<ChannelVideoBean?> {
            return arrayOfNulls(size)
        }
    }

}