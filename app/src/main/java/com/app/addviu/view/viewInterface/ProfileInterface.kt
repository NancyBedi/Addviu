package com.app.addviu.view.viewInterface

import okhttp3.MultipartBody

interface ProfileInterface {
    fun getUserDetails()
    fun updateImage(builder:MultipartBody.Builder)
    fun uploadBanner(builder: MultipartBody.Builder)
    fun removeBanner(builder: HashMap<String, String>)
    fun removeImage(builder: HashMap<String, String>)

}