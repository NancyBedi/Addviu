package com.app.addviu.view.fragments

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.SharedPrefsHelper
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer

open class BaseFragment:Fragment() {

    var sharedPrefsHelper: SharedPrefsHelper? = null
    var imageLoader: ImageLoader = ImageLoader.getInstance()
    val PICK_FROM_CAMERA = 1
    val SELECT_PICTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPrefsHelper = AppController.instance?.sharedHelper

        if (!imageLoader.isInited) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(activity))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setProgressVisible(progressBar: ProgressBar,recyclerView: RecyclerView){
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    fun setProgressGone(progressBar: ProgressBar,recyclerView: RecyclerView){
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    fun profilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.loading)
            .displayer(SimpleBitmapDisplayer()).build()
    }

    fun curveProfilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.circle_user)
            .displayer(RoundedBitmapDisplayer(15)).build()
    }

    fun roundProfilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.circle_user)
            .displayer(RoundedBitmapDisplayer(200)).build()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermissions(context: Context?, permissions: Array<String>?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

}