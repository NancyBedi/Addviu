package com.app.addviu.model.latestVidModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LatestVidData {
    @SerializedName("current_page")
    @Expose
    var currentPage = 0

    @SerializedName("data")
    @Expose
    var data = ArrayList<LatestVidListData>()

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
}