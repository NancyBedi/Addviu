package com.app.addviu.model.homeModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrendingData {

    @SerializedName("current_page")
    @Expose
    var currentPage: Int = 0
    @SerializedName("data")
    @Expose
    var data = ArrayList<HomeData>()
    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String = ""
    @SerializedName("from")
    @Expose
    var from: Int = 0
    @SerializedName("last_page")
    @Expose
    var lastPage: Int = 0
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
    var perPage: Int = 0
    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: String = ""
    @SerializedName("to")
    @Expose
    var to: Int = 0
    @SerializedName("total")
    @Expose
    var total: Int = 0

}