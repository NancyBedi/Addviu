package com.app.addviu.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.REQUEST_GALLERY_IMAGE
import com.app.addviu.data.helper.RealPathUtil
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.channelDetailBean.ChannelDetailBean
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import kotlinx.android.synthetic.main.channel_home_fragment.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ChannelHome(var channelId:String, var isUserChannel:Boolean):BaseFragment(), ResponseCallback, YesClick
    {

    var adapter: ChannelListAdapter?=null
    private var viewClicked = ""
    private val PERMISSION_ALL = 1
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var selectedImage: Uri? = null
    var imageFile = File("")
    var type = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.channel_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isUserChannel){
            editBanner.visibility = GONE
            editImage.visibility = GONE
        }else{
            editBanner.visibility = VISIBLE
            editImage.visibility = VISIBLE
        }
        editBanner.setOnClickListener {
            type = "banner"
            bannerPop()
//            showPopup(editBanner)
        }
        editImage.setOnClickListener {
            type = "channelImg"
            channelPop()
        }
    }

    override fun <T> success(t: T) {
        if (t is CommonSuccess) {
            if (t.status == 1) {
                channelDetail()
                Util.showToast(t.message, activity!!)
            } else {
                Util.showToast(t.message, activity!!)
            }
        } else if(t is SignUpBean){
            if (t.status == 1) {
                channelDetail()
                Util.showToast(t.message, activity!!)
            } else {
                Util.showToast(t.message, activity!!)
            }
        } else if (t is ChannelDetailBean){
            if (t.status == 1){
                if (t.data.banner != null && t.data.banner.isNotEmpty()) {
                    imageLoader.displayImage(t.data.banner, bannerImage, profilePic())
                }
                if (t.data.coverImage != null && t.data.coverImage.isNotEmpty()) {
                    imageLoader.displayImage(t.data.coverImage, channelImg, circleProfilePic())
                }
                channelNametxt.text = t.data.channelName
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    fun circleProfilePic(): DisplayImageOptions {
        return DisplayImageOptions.Builder().cacheOnDisk(true).showImageOnLoading(R.drawable.circle_user)
            .displayer(RoundedBitmapDisplayer(250)).build()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            REQUEST_GALLERY_IMAGE -> try {
                selectedImage = data?.data
                val file = File(RealPathUtil.getRealPath(activity, selectedImage))
                imageFile = Util.saveBitmapToFile(file)!!
                if (type.equals("channelImg")){
                    imageLoader.displayImage(selectedImage.toString(), channelImg, circleProfilePic())
                    updateChannelImage()
                }else{
                    imageLoader.displayImage(selectedImage.toString(), bannerImage, profilePic())
                    updateBanner()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ALL && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            openGalleryForImage()
        }
    }

    fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

   fun updateChannelImage(){
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (selectedImage?.path != null) {
            builder.addFormDataPart(
                "cover_image",
                imageFile.name,
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            builder.addFormDataPart("cover_image", "")
        }
       AppController.instance?.dataManager?.updateChannelImage(builder, channelId, this, activity)
   }

    fun updateBanner(){
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (selectedImage?.path != null) {
            builder.addFormDataPart(
                "banner",
                imageFile.name,
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            builder.addFormDataPart("banner", "")
        }
        AppController.instance?.dataManager?.updateChannelBanner(builder, channelId, this, activity)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser){
            channelDetail()
        }
    }

    override fun yesClick() {
        if (type.equals("banner")){
            AppController.instance?.dataManager?.removeChannelBanner(channelId, this, activity)
        }else{
            AppController.instance?.dataManager?.removeChannelImage(channelId, this, activity)
        }
    }

    fun channelPop(){
        val popupMenu: PopupMenu = PopupMenu(activity, editImage)
        popupMenu.menuInflater.inflate(R.menu.image_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.upload -> {
                    viewClicked = "image"
                    if (hasPermissions(activity, PERMISSIONS)) {
                        openGalleryForImage()
                    } else {
                        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL)
                    }
//                        (context as MyChannels).getData(data)
                }
                R.id.remove -> {
                    Util.showDeleteDialog(
                        activity!!,
                        "Are you really want to remove this channel image",
                        this
                    )
                }
            }
            true
        })
        popupMenu.show()
    }

    fun bannerPop(){
        val popupMenu: PopupMenu = PopupMenu(activity, editBanner)
        popupMenu.menuInflater.inflate(R.menu.image_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.upload -> {
                    viewClicked = "banner"
                    if (hasPermissions(activity, PERMISSIONS)) {
                        openGalleryForImage()
                    } else {
                        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL)
                    }
//                        (context as MyChannels).getData(data)
                }
                R.id.remove -> {
                    Util.showDeleteDialog(
                        activity!!,
                        "Are you really want to remove this banner image",
                        this
                    )
                }
            }
            true
        })
        popupMenu.show()
    }


//    fun showPopup(v: View?) {
//        val popup = PopupMenu(activity, v)
//        val inflater: MenuInflater = popup.menuInflater
//        inflater.inflate(R.menu.image_menu, popup.menu)
//        popup.show()
//    }


    fun channelDetail(){
        AppController.instance?.dataManager?.channelDetails(channelId, this, activity)
    }

//    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.upload -> {
//                viewClicked = "banner"
//                if (hasPermissions(activity, PERMISSIONS)) {
//                    openGalleryForImage()
//                } else {
//                    requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL)
//                }
//                true
//            }
//            R.id.remove -> {
//                Util.showDeleteDialog(
//                    activity!!,
//                    "Are you really want to remove this banner image",
//                    this
//                )
//                true
//            }
//            else -> false
//        }
//    }
}