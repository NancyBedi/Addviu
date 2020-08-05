package com.app.addviu.model.homeVideoModel

import android.os.Parcel
import android.os.Parcelable
import com.app.addviu.model.homeModel.HomeData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Success() : Parcelable {
    @SerializedName("current_page")
    @Expose
    var currentPage = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<HomeData>()

    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String = ""

    @SerializedName("from")
    @Expose
    var from = 0

    @SerializedName("last_page")
    @Expose
    var lastPage = 0

    @SerializedName("last_page_url")
    @Expose
    var lastPageUrl: String = ""

    @SerializedName("next_page_url")
    @Expose
    var nextPageUrl: String = ""

    @SerializedName("path")
    @Expose
    var path: String = ""

    @SerializedName("per_page")
    @Expose
    var perPage = 0

    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: String = ""

    @SerializedName("to")
    @Expose
    var to = 0

    @SerializedName("total")
    @Expose
    var total = 0

    constructor(parcel: Parcel) : this() {
        currentPage = parcel.readInt()
        firstPageUrl = parcel.readString()?:""
        from = parcel.readInt()
        lastPage = parcel.readInt()
        lastPageUrl = parcel.readString()?:""
        nextPageUrl = parcel.readString()?:""
        path = parcel.readString()?:""
        perPage = parcel.readInt()
        prevPageUrl = parcel.readString()?:""
        to = parcel.readInt()
        total = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentPage)
        parcel.writeString(firstPageUrl)
        parcel.writeInt(from)
        parcel.writeInt(lastPage)
        parcel.writeString(lastPageUrl)
        parcel.writeString(nextPageUrl)
        parcel.writeString(path)
        parcel.writeInt(perPage)
        parcel.writeString(prevPageUrl)
        parcel.writeInt(to)
        parcel.writeInt(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Success> {
        override fun createFromParcel(parcel: Parcel): Success {
            return Success(parcel)
        }

        override fun newArray(size: Int): Array<Success?> {
            return arrayOfNulls(size)
        }
    }

}