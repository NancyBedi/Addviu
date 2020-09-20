package com.app.addviu.data

import android.content.Context
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.data.helper.USER_ID
import com.app.addviu.data.retrofitRequest.ProgressRequestBody
import com.app.addviu.data.retrofitRequest.ServiceHandler
import com.app.addviu.view.activity.VideoUploadScreen
import com.app.addviu.view.viewInterface.ResponseCallback
import okhttp3.MultipartBody
import java.io.File


class DataManager(
    private val mSharedPrefsHelper: SharedPrefsHelper,
    private val mServiceHandler: ServiceHandler
) {

//    fun getStates(responseCallback: ResponseCallback, context: Context) {
//
//        mServiceHandler.getStates(responseCallback, context)
//    }
//
//    fun getLoginType(responseCallback: ResponseCallback, context: Context) {
//
//        mServiceHandler.getLoginType(responseCallback, context)
//    }


    fun getHomeVideoData(pageId:Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getHomeVideoData(pageId, responseCallback, context)
    }

    fun getTrendingVideoData(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getTrendingVideoData(pageId, responseCallback, context)
    }

    fun latesVideos(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.latesVideos(pageId, responseCallback, context)
    }

    fun entertainmentAndComedyVideos(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.entertainmentAndComedyVideos(pageId, responseCallback, context)
    }

    fun latestNewsVideos(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.latestNewsVideos(pageId, responseCallback, context)
    }

    fun womenSpecialVideos(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.womenSpecialVideos(pageId, responseCallback, context)
    }

    fun suggestedVideos(pageId: Int, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.suggestedVideos(pageId, responseCallback, context)
    }

    fun signUpUser(
        map: HashMap<String, String>,
        responseCallback: ResponseCallback,
        context: Context?
    ) {
        mServiceHandler.signUpUser(map, responseCallback, context)
    }

    fun signInUser(
        map: HashMap<String, String>,
        responseCallback: ResponseCallback,
        context: Context?
    ) {
        mServiceHandler.signInUser(map, responseCallback, context)
    }

    fun forgotPassword(email: String, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.forgotPassword(email, responseCallback, context)
    }

    fun uploadVideo(
        file: File,
        responseCallback: ResponseCallback,
        context: Context?,
        activity: VideoUploadScreen
    ) {
        val fileBody = ProgressRequestBody(file,"multipart/form-data",activity)
        val filePart = MultipartBody.Part.createFormData("video", file.name, fileBody)
        mServiceHandler.uploadVideo(filePart, responseCallback, context,activity)
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    fun addChannel(
//        createChannel: CreateChannelBean,
//        responseCallback: ResponseCallback?,
//        context: Context?,
//        isSelected: String
//    ) {
//
//        // MultipartBody.Part is used to send also the actual file name
//        var body: MultipartBody.Part? = null
//        val file: File
//
//        file = if (isSelected == "Gallery") {
//            FileUtils.getFile(context, createChannel.imageUri)
//        } else {
//            File(createChannel.imagePath)
//        }
//
//        body = if (createChannel.imagePath.equals("") && createChannel.imageUri == null) {
//            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), "")
//            MultipartBody.Part.createFormData("cover_image", "", requestFile)
//        } else {
//            val requestFile = RequestBody.create(
//                "image/*".toMediaTypeOrNull(), file
//            )
//            MultipartBody.Part.createFormData("cover_image", file.name, requestFile)
//        }
//
//
//        mServiceHandler.addChannel(responseCallback, createChannel, body, context)
//    }



    fun deleteVideo(originalName: String, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.deleteVideo(originalName, responseCallback, context)
    }

    fun deleteChannel(channelId: String, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.deleteChannel(channelId, responseCallback, context)
    }

    fun deletePlaylist(playlist_id: String, responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.deletePlaylist(playlist_id, responseCallback, context)
    }

    fun getUserDetails(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getUserDetails(responseCallback, context)
    }

    fun logout(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.logout(responseCallback, context)
    }

    fun getCategories(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getCategories(responseCallback, context)
    }

    fun getNotifications(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getNotifications(responseCallback, context)
    }

    fun clearNotifications(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.clearNotifications(responseCallback, context)
    }

//    fun latesVideos(responseCallback: ResponseCallback, context: Context?) {
//        mServiceHandler.latesVideos(responseCallback, context)
//    }

    fun getUserChannels(responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getUserChannels(mSharedPrefsHelper[USER_ID,0]!!,responseCallback, context)
    }

    fun getChannelPlaylist(channelId:Int,responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.getChannelPlaylist(channelId,responseCallback, context)
    }

    fun uploadVideoData(builder:MultipartBody.Builder,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.uploadVideoData(builder,responseCallback, context)
    }

    fun addChannel(builder:MultipartBody.Builder,responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.addChannel(builder, responseCallback, context)
    }

    fun updateChannel(builder:MultipartBody.Builder,responseCallback: ResponseCallback, context: Context?) {
        mServiceHandler.updateChannel(builder, responseCallback, context)
    }

    fun createPlaylist(builder:MultipartBody.Builder, responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.createPlaylist(builder,responseCallback, context)
    }

    fun updatePlaylist(builder:MultipartBody.Builder, responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.updatePlaylist(builder,responseCallback, context)
    }



    fun updateChannelBanner(builder:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.updateChannelBanner(builder, channelId, responseCallback, context)
    }

    fun removeChannelBanner(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.removeChannelBanner(channelId,responseCallback, context)
    }

    fun updateChannelImage(builder:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.updateChannelImage(builder, channelId, responseCallback, context)
    }

    fun removeChannelImage(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.removeChannelImage(channelId,responseCallback, context)
    }


    fun updatePlaylistBanner(builder:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.updatePlaylistBanner(builder, channelId, responseCallback, context)
    }

    fun removePlaylistBanner(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.removePlaylistBanner(channelId,responseCallback, context)
    }

    fun updatePlaylistImage(builder:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.updatePlaylistImage(builder, channelId, responseCallback, context)
    }

    fun removePlaylistImage(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.removePlaylistImage(channelId,responseCallback, context)
    }


    fun getRelatedVideos(uid:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.getRelatedVideos(uid,responseCallback, context)
    }

    fun getRelatedComments(uid:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.getRelatedComments(uid,responseCallback, context)
    }

    fun getRelatedReplies(commentId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.getRelatedReplies(commentId,responseCallback, context)
    }

    fun channelPlaylists(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.channelPlaylists(channelId,responseCallback, context)
    }

    fun channelVideos(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.channelVideos(channelId,responseCallback, context)
    }

    fun channelDetails(channelId:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.channelDetails(channelId,responseCallback, context)
    }

    fun playlistVideos(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.playlistVideos(playlist_id,responseCallback, context)
    }

    fun playlistDetails(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.playlistDetails(playlist_id,responseCallback, context)
    }

    fun createComment(uid:String,map: HashMap<String, String>,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.createComment(uid,map,responseCallback, context)
    }

    fun search(map: HashMap<String, String>,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.search(map,responseCallback, context)
    }

    fun searchTrend(map: HashMap<String, String>,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.searchTrend(map,responseCallback, context)
    }

    fun userSubscribedChannels(map: HashMap<String, String>,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.userSubscribedChannels(map,responseCallback, context)
    }

    fun getSubscribeUser(channelSlug:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.getSubscribeUser(channelSlug,responseCallback, context)
    }

    fun subscribeChannel(channelSlug:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.subscribeChannel(channelSlug,responseCallback, context)
    }


    fun getVideoVotes(uid:String,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.getVideoVotes(uid,responseCallback, context)
    }

    fun setVideoVotes(uid:String,map: HashMap<String, String>,responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.setVideoVotes(uid,map,responseCallback, context)
    }



//    fun getUserChannels(uid:String, responseCallback: ResponseCallback, context: Context?){
//        mServiceHandler.getUserChannels(uid,responseCallback, context)
//    }


    fun deleteComment(uid: String, commentId: String, responseCallback: ResponseCallback, context: Context?){
        mServiceHandler.deleteComment(uid,commentId,responseCallback, context)
    }
//    fun loginManager(
//        loginReqBean: LoginReqBean,
//        responseCallback: ResponseCallback,
//        context: Context
//    ) {
//        mServiceHandler.loginManager(responseCallback, loginReqBean, context)
//    }
//
//    fun facebookLogin(
//        loginReqBean: FacebookReqBean,
//        responseCallback: ResponseCallback,
//        context: Context
//    ) {
//        mServiceHandler.facebookLogin(responseCallback, loginReqBean, context)
//    }


}