package com.app.addviu.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.REQUEST_GALLERY_IMAGE
import com.app.addviu.data.helper.RealPathUtil
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.DeleteCommonBean
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.PlaylistBean
import com.app.addviu.model.videoModel.PlaylistData
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.adapter.ChannelListAdapter
import com.app.addviu.view.adapter.PlaylistAdapter
import com.app.addviu.view.viewInterface.PlaylistClick
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.add_channel_layout.*
import kotlinx.android.synthetic.main.channel_home_fragment.*
import kotlinx.android.synthetic.main.channel_playlist_fragment.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ChannelPlaylist(val channelId:String):BaseFragment(), ResponseCallback {
    var adapter: ChannelListAdapter?=null
    val playlistData = PlaylistData()
    private var viewClicked = ""
    private val PERMISSION_ALL = 1
    private val PERMISSIONS = arrayOf(
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
        return inflater.inflate(R.layout.channel_playlist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewPlaylist.setOnClickListener {
            newPlaylist(activity!!,"add", playlistData)
        }
    }

    override fun <T> success(t: T) {
        if (t is PlaylistBean){
            if (t.status == 1){
                if (t.data.size >= 1 ) {
                    recycle_playlist.adapter = PlaylistAdapter(imageLoader, t.data, activity!!, this)
                }else{
                    Util.showToast("No Playlist found!" , activity!!)
                }
            }else{
                Util.showToast("No Playlist found!" , activity!!)
            }
        }else if (t is SignUpBean){
            if (t.status == 1){
                showPlaylist()
                Util.showToast(t.message, activity!!)
            }else{
                Util.showToast(t.message, activity!!)
            }
        }else if (t is CommonSuccess){
            if (t.status == 1){
                showPlaylist()
                Util.showToast(t.message, activity!!)
            }else{
                Util.showToast(t.message, activity!!)
            }
        }else if (t is DeleteCommonBean){
            if (t.status == 1){
                showPlaylist()
                Util.showToast(t.message, activity!!)
            }else{
                Util.showToast(t.message, activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

//    override fun onResume() {
//        super.onResume()
//        showPlaylist()
//    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            showPlaylist()
    }


     fun newPlaylist(context: Context, type:String, playlistData: PlaylistData) {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val alertLayout = inflater.inflate(R.layout.dialog_new_playlist, null)
        val titleDialog = alertLayout.findViewById<TextView>(R.id.titleDialog)
        val descDialog = alertLayout.findViewById<TextView>(R.id.descDialog)
        val btnCreate = alertLayout.findViewById<TextView>(R.id.btnCreate)
        val btnImg = alertLayout.findViewById<TextView>(R.id.btnImg)
        val btnCancel = alertLayout.findViewById<TextView>(R.id.btnCancel)

        val alert = android.app.AlertDialog.Builder(context)
        val a = alert.create()
        //        a.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        a.setView(alertLayout, 0, 0, 0, 0)
        a.show()
         var playlistId = ""
        if (type.equals("edit")){
            titleDialog.text = playlistData.playlistName
            descDialog.text = playlistData.description
            playlistId = playlistData.id.toString()
            btnCreate.text = "Save"
        }

        btnCancel.setOnClickListener { a.dismiss() }

        btnImg.setOnClickListener {
            viewClicked = "image"
            if (hasPermissions(activity, PERMISSIONS)) {
                openGalleryForImage()
            } else {
                requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL)
            }
        }

        btnCreate.setOnClickListener {
            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            if (selectedImage?.path != null) {
                builder.addFormDataPart(
                    "playlist_icon",
                    imageFile.name,
                    imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            } else {
                builder.addFormDataPart("playlist_icon", "")
            }

            builder.addFormDataPart("playlist_name", titleDialog.text.toString())
            builder.addFormDataPart("description", descDialog.text.toString())
            if (type.equals("edit")){
                builder.addFormDataPart("playlist_id", playlistId)
                AppController.instance?.dataManager?.updatePlaylist(builder, this, activity)
            }else{
                builder.addFormDataPart("channel_id", channelId)
                AppController.instance?.dataManager?.createPlaylist(builder, this, activity)
            }
            a.dismiss()
        }
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
//                imageLoader.displayImage(selectedImage.toString(), channelImg, roundProfilePic())

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

    fun showPlaylist(){
        AppController.instance?.dataManager?.channelPlaylists(channelId, this, activity)
    }

}