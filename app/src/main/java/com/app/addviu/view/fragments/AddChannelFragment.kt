package com.app.addviu.view.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.REQUEST_GALLERY_IMAGE
import com.app.addviu.data.helper.RealPathUtil
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.CreateChannelBean
import com.app.addviu.model.videoModel.ChannelData
import com.app.addviu.presenter.ChannelPresenter
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_rewards_screen.*
import kotlinx.android.synthetic.main.add_channel_layout.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


class AddChannelFragment : BaseFragment(), ResponseCallback, View.OnClickListener {
    val createChannelBean = CreateChannelBean()
    private var channelPresenter: ChannelPresenter? = null
    var alertDialog: AlertDialog? = null
    private var viewClicked = ""
    var channelId = ""
    var isEdited = false
    var isSelected = ""
    var picturePath = ""
    var catId = ""
    lateinit var mBitmap: Bitmap
    private val PERMISSION_ALL = 1
    private val PERMISSIONS = arrayOf(
//        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var selectedImage: Uri? = null
    var imageFile = File("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        channelPresenter = ChannelPresenter(activity!!)
        return inflater.inflate(R.layout.add_channel_layout, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thumbImage.setOnClickListener(this)
        imageIcon.setOnClickListener(this)

        btnSubmit.setOnClickListener {
            if (isEdited) {
                if (checkValidation("edit")) {
                    editChannel()
                }
            } else {
                if (checkValidation("add")) {
                    addChannel()
                }
            }
        }

        categoryEditText.setOnClickListener {
            if ((context as MyChannels).categoryList.size == 0) {
                channelPresenter?.getCategories()
            } else {
                (context as MyChannels).showCategoryDialog((context as MyChannels).categoryList)
            }
        }
    }

    fun setData(channelData: ChannelData, isEdit: Boolean) {
        nameEditText.setText(channelData.channelName)
        imageLoader.displayImage(channelData.coverImage, thumbImage)
        imageIcon.visibility = GONE
        channelId = channelData.id.toString()
        categoryEditText.setText(channelData.category_name)
        catId = channelData.categoryId.toString()
        desEditText.setText(channelData.description)
        isEdited = isEdit
        btnSubmit.text = "Edit Channel"
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun addChannel() {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (selectedImage?.path != null) {
//            val file = File(RealPathUtil.getRealPath(activity, selectedImage))
            builder.addFormDataPart(
                "cover_image",
                imageFile.name,
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            builder.addFormDataPart("cover_image", "")
        }
        builder.addFormDataPart("channel_name", nameEditText.text.toString())
        if ((context as MyChannels).categoryId.toString()
                .isNotEmpty() && !(context as MyChannels).categoryId.toString().equals("0")
        ) {
            catId = (context as MyChannels).categoryId.toString()
        } else {
            catId = "17"
        }
        builder.addFormDataPart("category", catId)
        builder.addFormDataPart("description", desEditText.text.toString())

        AppController.instance?.dataManager?.addChannel(builder, this, activity)

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun editChannel() {

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (selectedImage?.path != null) {
//            val file = File(RealPathUtil.getRealPath(activity, selectedImage))
            builder.addFormDataPart(
                "cover_image",
                imageFile.name,
                imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        } else {
            builder.addFormDataPart("cover_image", "")
        }
        builder.addFormDataPart("channel_id", channelId)
        builder.addFormDataPart("channel_name", nameEditText.text.toString())
//        if (catId.isEmpty()) {
        if ((context as MyChannels).categoryId.toString()
                .isNotEmpty() && !(context as MyChannels).categoryId.toString().equals("0")
        ) {
            catId = (context as MyChannels).categoryId.toString()
        } else {
            if (catId.isEmpty() || catId.equals("0")) {
                catId = "17"
            }
        }
//        }
        builder.addFormDataPart("category_id", catId)
        builder.addFormDataPart("description", desEditText.text.toString())

        AppController.instance?.dataManager?.updateChannel(builder, this, activity)

    }


    override fun <T> success(t: T) {
        if (t is CommonSuccess) {
            if (t.status == 1) {
                Util.showToast(t.message, activity!!)
                nameEditText.setText("")
                categoryEditText.setText("")
                desEditText.setText("")
                imageIcon.visibility = VISIBLE
                imageLoader.displayImage("", thumbImage, Util.profilePic())
                (context as MyChannels).viewPager.setCurrentItem(1, true)
                (context as MyChannels).changeData()
                btnSubmit.text = "Add Channel"
            } else {
                Util.showToast(t.message, activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {
        Util.showToast(t.toString(), activity!!)
    }

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: java.lang.Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK)
            return

        when (requestCode) {
            REQUEST_GALLERY_IMAGE -> try {
                selectedImage = data?.data
                val file = File(RealPathUtil.getRealPath(activity, selectedImage))
                imageFile = saveBitmapToFile(file)!!

                imageLoader.displayImage(selectedImage.toString(), thumbImage, curveProfilePic())
                imageIcon.visibility = View.GONE
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
//            Util.showImageAlertDialog(activity!!)
            openGalleryForImage()
        }
    }

    fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

    private fun checkValidation(type: String): Boolean {

        if (Util.checkEmpty(nameEditText, "Channel name", activity!!)) {
            return false
        }

        if (Util.checkEmpty(categoryEditText, "Category", activity!!)) {
            return false
        }

        if (type.equals("add")) {
            if (selectedImage?.path.isNullOrEmpty()) {
                Util.showToast("Select Channel Image", activity!!)
                return false
            }
            if (Util.checkEmpty(desEditText, "Description", activity!!)) {
                return false
            }
        }
        return true
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.thumbImage, R.id.imageIcon ->{
                viewClicked = "image"
                if (hasPermissions(activity, PERMISSIONS)) {
                    openGalleryForImage()
                } else {
                    requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL)
                }
            }
        }
    }
}