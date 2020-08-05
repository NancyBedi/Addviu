package com.app.addviu.model.homeVideoModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeVideoBean() : Parcelable {
    @SerializedName("success")
    @Expose
    var success = Success()

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeVideoBean> {
        override fun createFromParcel(parcel: Parcel): HomeVideoBean {
            return HomeVideoBean(parcel)
        }

        override fun newArray(size: Int): Array<HomeVideoBean?> {
            return arrayOfNulls(size)
        }
    }

}