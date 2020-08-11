package com.app.addviu.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.*
import com.app.addviu.presenter.ProfilePresenter
import com.app.addviu.presenter.SignUpPresenter
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.adapter.ChannelHomeAdapter
import com.app.addviu.view.fragments.*
import com.app.addviu.view.viewInterface.YesClick
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_channel_home.*
import kotlinx.android.synthetic.main.activity_channel_home.backImage
import kotlinx.android.synthetic.main.activity_channel_home.tabLayout
import kotlinx.android.synthetic.main.activity_channel_home.viewPager
import kotlinx.android.synthetic.main.activity_my_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ProfilePage : BaseActivity(), View.OnClickListener, YesClick {
    private var viewClicked = ""
    private val PERMISSION_ALL = 1
    private val PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var selectedImage: Uri? = null
    var imageFile = File("")
    var type = ""
    private val presenter = ProfilePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        presenter.getUserDetails()
        val adapter = ChannelHomeAdapter(getSupportFragmentManager())
        adapter.addFragment(MyProfileFragment(), "My Profile")
        adapter.addFragment(SubscribeChannelFragment(), "Subscribed Channel")

        viewPager.setAdapter(adapter)
        tabLayout.setupWithViewPager(viewPager)
        setClicks()

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.backImage -> {
                finish()
            }
            R.id.uploadImage -> {
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    startActivity(Intent(this, VideoUploadScreen::class.java))
                } else {
                    startActivityForResult(Intent(this, SignInScreen::class.java), SIGN_IN_CODE)
                }
            }
            R.id.banCamera -> {
                type = "banner"
                bannerPop()
            }
            R.id.userCamera -> {
                type = "channelImg"
                channelPop()
            }
        }
    }


    fun channelPop() {
        val popupMenu: PopupMenu = PopupMenu(this, userCamera)
        popupMenu.menuInflater.inflate(R.menu.image_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.upload -> {
                    viewClicked = "image"
                    if (hasPermissions(this, PERMISSIONS)) {
                        openGalleryForImage()
                    } else {
                        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL, this)
                    }
//                        (context as MyChannels).getData(data)
                }
                R.id.remove -> {
                    Util.showDeleteDialog(
                        this,
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
        val popupMenu: PopupMenu = PopupMenu(this, banCamera)
        popupMenu.menuInflater.inflate(R.menu.image_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.upload -> {
                    viewClicked = "banner"
                    if (hasPermissions(this, PERMISSIONS)) {
                        openGalleryForImage()
                    } else {
                        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL, this)
                    }
//                        (context as MyChannels).getData(data)
                }
                R.id.remove -> {
                    Util.showDeleteDialog(
                        this,
                        "Are you really want to remove this banner image",
                        this
                    )
                }
            }
            true
        })
        popupMenu.show()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            REQUEST_GALLERY_IMAGE -> try {
                selectedImage = data?.data
                val file = File(RealPathUtil.getRealPath(this, selectedImage))
                imageFile = Util.saveBitmapToFile(file)!!
                if (type.equals("channelImg")){
                    imageLoader.displayImage(selectedImage.toString(), userImage, circleProfilePic())
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

    fun updateChannelImage(){
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (selectedImage?.path != null) {
            builder.addFormDataPart(
                "avatar",
                imageFile.name,
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            builder.addFormDataPart("avatar", "")
        }
        builder.addFormDataPart("user_id", sharedPrefsHelper?.get(USER_ID, 0).toString())
        presenter.updateImage(builder)
//        AppController.instance?.dataManager?.updateChannelImage(builder, channelId, this, activity)
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
        builder.addFormDataPart("user_id", sharedPrefsHelper?.get(USER_ID, 0).toString())
        presenter.uploadBanner(builder)
//        AppController.instance?.dataManager?.updateBanner(builder, this, activity)
    }


    fun setClicks() {
        backImage.setOnClickListener(this)
        uploadImage.setOnClickListener(this)
        banCamera.setOnClickListener(this)
        userCamera.setOnClickListener(this)
    }

    override fun yesClick() {
        if (type.equals("banner")){
            val map = HashMap<String, String>()
            map["user_id"] = sharedPrefsHelper?.get(USER_ID, 0).toString()
            presenter.removeBanner(map)
        }else{
            val map = HashMap<String, String>()
            map["user_id"] = sharedPrefsHelper?.get(USER_ID, 0).toString()
            presenter.removeImage(map)
        }
    }
}