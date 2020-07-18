package com.app.addviu.model.homeModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeBean() : Parcelable {
    @SerializedName("success")
    @Expose
    var data= ArrayList<HomeData>()

    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeBean> {
        override fun createFromParcel(parcel: Parcel): HomeBean {
            return HomeBean(parcel)
        }

        override fun newArray(size: Int): Array<HomeBean?> {
            return arrayOfNulls(size)
        }
    }
}