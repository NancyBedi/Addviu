package com.app.addviu.model.homeModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrendingBean() : Parcelable {
    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("data")
    @Expose
    var data = TrendingData()

    @SerializedName("message")
    @Expose
    var message = ""

    constructor(parcel: Parcel) : this() {
        status = parcel.readInt()
        message = parcel.readString()?:""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrendingBean> {
        override fun createFromParcel(parcel: Parcel): TrendingBean {
            return TrendingBean(parcel)
        }

        override fun newArray(size: Int): Array<TrendingBean?> {
            return arrayOfNulls(size)
        }
    }


}