package com.app.addviu.data.retrofitRequest

import android.app.Activity
import android.content.Context
import com.app.addviu.AppController
import com.app.addviu.model.CreateChannelBean
import com.app.addviu.view.activity.VideoUploadScreen
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.app.smartboard.data.retrofitRequest.WebServices
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceHandler(apiClient: ApiClient) {
    private var builder: WebServices = apiClient.retrofit!!.create(WebServices::class.java)

    //    fun getStates(
//        responseCallback: ResponseCallback,
//        context: Context
//    ) {
//        val call = builder.getStates()
//        getResponse(call, responseCallback, context)
//    }

    fun getHomeVideoData(responseCallback: ResponseCallback, context: Context?) {
        val call = builder.getHomeVideoList()
        getResponse(call, responseCallback, context)
    }

    fun getTrendingVideoData(pageId:Int,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.getTrendingVideoList(pageId)
        getResponse(call, responseCallback, context)
    }

    fun signUpUser(map:HashMap<String,String>,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.signUpUser(map)
        getResponse(call, responseCallback, context)
    }

    fun signInUser(map:HashMap<String,String>,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.signInUser(map)
        getResponse(call, responseCallback, context)
    }

    fun forgotPassword(email:String,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.forgotPassword(email)
        getResponse(call, responseCallback, context)
    }

    fun uploadVideo(video: MultipartBody.Part, responseCallback: ResponseCallback,
                      context: Context?,activity: VideoUploadScreen) {
        val call = builder.uploadVideo(video)
        activity.getRunningRequest(call)
        getResponse(call, responseCallback, context)
    }

//    fun addChannel(video: MultipartBody.Part, responseCallback: ResponseCallback,
//                    context: Context?,activity: VideoUploadScreen) {
//        val call = builder.addChannel(video)
//        activity.getRunningRequest(call)
//        getResponse(call, responseCallback, context)
//    }

//    fun addChannel(
//        responseCallback: ResponseCallback?,
//        setupProfileData: CreateChannelBean,
//        part: MultipartBody.Part?,
//        context: Context?
//    ) {
//        val call: Call<*> = builder.addChannel(
//            createRequestBody(setupProfileData.channelname),
//            createRequestBody(setupProfileData.category),
//            createRequestBody(setupProfileData.description),
//            part
//        )
//        getResponse(call, responseCallback, context)
//    }

    private fun createRequestBody(value: String): RequestBody? {
        return RequestBody.create(MultipartBody.FORM, value)
    }

    fun deleteVideo(originalName:String, responseCallback: ResponseCallback,
                       context: Context?) {
        val call = builder.deleteVideo(originalName)
        getResponse(call, responseCallback, context)
    }

    fun deleteChannel(channelId:String, responseCallback: ResponseCallback,
                    context: Context?) {
        val call = builder.deleteChannel(channelId)
        getResponse(call, responseCallback, context)
    }

    fun deletePlaylist(playlist_id:String, responseCallback: ResponseCallback,
                      context: Context?) {
        val call = builder.deletePlaylist(playlist_id)
        getResponse(call, responseCallback, context)
    }


    fun getUserDetails(responseCallback: ResponseCallback,
                    context: Context?) {
        val call = builder.getUserDetails()
        getResponse(call, responseCallback, context)
    }


    fun logout(responseCallback: ResponseCallback,
                       context: Context?) {
        val call = builder.logout()
        getResponse(call, responseCallback, context)
    }

    fun getCategories(responseCallback: ResponseCallback,
                       context: Context?) {
        val call = builder.getCategories()
        getResponse(call, responseCallback, context)
    }

    fun getNotifications(responseCallback: ResponseCallback,
                      context: Context?) {
        val call = builder.getNotifications()
        getResponse(call, responseCallback, context)
    }

    fun clearNotifications(responseCallback: ResponseCallback,
                         context: Context?) {
        val call = builder.clearNotifications()
        getResponse(call, responseCallback, context)
    }

    fun latesVideos(responseCallback: ResponseCallback,
                      context: Context?) {
        val call = builder.latesVideos()
        getResponse(call, responseCallback, context)
    }

    fun entertainmentAndComedyVideos(responseCallback: ResponseCallback,
                    context: Context?) {
        val call = builder.entertainmentAndComedyVideos()
        getResponse(call, responseCallback, context)
    }

    fun latestNewsVideos(responseCallback: ResponseCallback,
                                     context: Context?) {
        val call = builder.latestNewsVideos()
        getResponse(call, responseCallback, context)
    }

    fun womenSpecialVideos(responseCallback: ResponseCallback,
                         context: Context?) {
        val call = builder.womenSpecialVideos()
        getResponse(call, responseCallback, context)
    }

    fun suggestedVideos(responseCallback: ResponseCallback,
                           context: Context?) {
        val call = builder.suggestedVideos()
        getResponse(call, responseCallback, context)
    }


    fun getUserChannels(userId:Int,responseCallback: ResponseCallback,
                      context: Context?) {
        val call = builder.getUserChannels(userId)
        getResponse(call, responseCallback, context)
    }

    fun getChannelPlaylist(channelId:Int,responseCallback: ResponseCallback,
                        context: Context?) {
        val call = builder.getChannelPlaylist(channelId)
        getResponse(call, responseCallback, context)
    }

    fun uploadVideoData(builder1:MultipartBody.Builder,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.uploadVideoData(builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun addChannel(builder1:MultipartBody.Builder,responseCallback: ResponseCallback, context: Context?) {
        val call = builder.addChannel(builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun updateChannel(builder1:MultipartBody.Builder, responseCallback: ResponseCallback, context: Context?) {
        val call = builder.updateChannel(builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun createPlaylist(builder1:MultipartBody.Builder, responseCallback: ResponseCallback, context: Context?){
        val call = builder.createPlaylist(builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun updatePlaylist(builder1:MultipartBody.Builder, responseCallback: ResponseCallback, context: Context?){
        val call = builder.updatePlaylist(builder1.build())
        getResponse(call, responseCallback, context)
    }


    fun updateChannelBanner(builder1:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.updateChannelBanner(channelId, builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun removeChannelBanner(channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.removeChannelBanner(channelId)
        getResponse(call, responseCallback, context)
    }

    fun updateChannelImage(builder1:MultipartBody.Builder,channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.updateChannelImage(channelId, builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun removeChannelImage(channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.removeChannelImage(channelId)
        getResponse(call, responseCallback, context)
    }

    fun updatePlaylistBanner(builder1:MultipartBody.Builder,playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.updatePlaylistBanner(playlist_id, builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun removePlaylistBanner(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.removePlaylistBanner(playlist_id)
        getResponse(call, responseCallback, context)
    }

    fun updatePlaylistImage(builder1:MultipartBody.Builder,playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.updatePlaylistImage(playlist_id, builder1.build())
        getResponse(call, responseCallback, context)
    }

    fun removePlaylistImage(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.removePlaylistImage(playlist_id)
        getResponse(call, responseCallback, context)
    }


    fun getRelatedVideos(uid:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.getRelatedVideos(uid)
        getResponse(call, responseCallback, context)
    }

    fun getRelatedComments(uid:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.getRelatedComments(uid)
        getResponse(call, responseCallback, context)
    }

    fun getRelatedReplies(commentId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.getRelatedRelies(commentId)
        getResponse(call, responseCallback, context)
    }

    fun channelPlaylists(channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.channelPlaylists(channelId)
        getResponse(call, responseCallback, context)
    }

    fun channelVideos(channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.channelVideos(channelId)
        getResponse(call, responseCallback, context)
    }

    fun channelDetails(channelId:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.channelDetails(channelId)
        getResponse(call, responseCallback, context)
    }

    fun playlistVideos(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.playlistVideos(playlist_id)
        getResponse(call, responseCallback, context)
    }

    fun playlistDetails(playlist_id:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.playlistDetails(playlist_id)
        getResponse(call, responseCallback, context)
    }

    fun createComment(uid:String,map:HashMap<String,String>,responseCallback: ResponseCallback, context: Context?){
        val call = builder.createComment(uid,map)
        getResponse(call, responseCallback, context)
    }

    fun search(map:HashMap<String,String>,responseCallback: ResponseCallback, context: Context?){
        val call = builder.search(map)
        getResponse(call, responseCallback, context)
    }

    fun searchTrend(map:HashMap<String,String>,responseCallback: ResponseCallback, context: Context?){
        val call = builder.searchTrend(map)
        getResponse(call, responseCallback, context)
    }

    fun getSubscribeUser(channelSlug:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.getSubscribeUser(channelSlug)
        getResponse(call, responseCallback, context)
    }

    fun subscribeChannel(channelSlug:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.subscribeChannel(channelSlug)
        getResponse(call, responseCallback, context)
    }

    fun getVideoVotes(uid:String,responseCallback: ResponseCallback, context: Context?){
        val call = builder.getVideoVotes(uid)
        getResponse(call, responseCallback, context)
    }

    fun setVideoVotes(uid:String, map: HashMap<String, String>, responseCallback: ResponseCallback, context: Context?){
        val call = builder.setVideoVotes(uid,map)
        getResponse(call, responseCallback, context)
    }

//    fun getUserChannels(uid:String, responseCallback: ResponseCallback, context: Context?){
//        val call = builder.getUserChannels(uid)
//        getResponse(call, responseCallback, context)
//    }



    fun deleteComment(uid:String, commentId: String, responseCallback: ResponseCallback, context: Context?){
        val call = builder.deleteComment(uid,commentId)
        getResponse(call, responseCallback, context)
    }

//    fun getSchool(
//        stateid: String,
//        cityid: String,
//        responseCallback: ResponseCallback,
//        context: Context
//    ) {
//        val call = builder.getSchool(stateid, cityid)
//        getResponse(call, responseCallback, context)
//    }


//    fun saveTranscriptData(
//        responseCallback: ResponseCallback, saveTransReqBean: SaveTransReqBean,
//        context: Context?
//    ) {
//
//
//        builder = apiClient.retrofit!!.create(WebServices::class.java)
//
//        val builder1 = MultipartBody.Builder()
//        builder1.setType(MultipartBody.FORM)
//
//
//        for (i in saveTransReqBean.docFile.indices) {
//            builder1.addFormDataPart(
//                "Uploads",
//                saveTransReqBean.docFile[i].name,
//                RequestBody.create(
//                    MediaType.parse("multipart/form-data"),
//                    saveTransReqBean.docFile[i]
//                )
//            )
//        }
//
//        builder1.addFormDataPart("studentid", saveTransReqBean.studentid)
//        builder1.addFormDataPart("classid", saveTransReqBean.classid)
//        builder1.addFormDataPart("schoolname", saveTransReqBean.schoolname)
//        builder1.addFormDataPart("avggrade", saveTransReqBean.avggrade)
//        builder1.addFormDataPart("avgper", saveTransReqBean.avgper)
//
//        val requestBody = builder1.build()
//        val call = builder.saveTranscriptData(requestBody)
//        getResponse(call, responseCallback, context)
//    }


//    fun updateECPData(
//        responseCallback: ResponseCallback, saveEcaEcp: SaveEcaEcpReqBean,
//        context: Context?
//    ) {
//
//        builder = apiClient.retrofit!!.create(WebServices::class.java)
//
//        val builder1 = MultipartBody.Builder()
//        builder1.setType(MultipartBody.FORM)
//
//        if (saveEcaEcp.docFile.path.isNullOrBlank()) {
//            builder1.addFormDataPart("Uploads", "")
//        } else {
//            builder1.addFormDataPart(
//                "Uploads",
//                saveEcaEcp.docFile.name,
//                RequestBody.create(
//                    MediaType.parse("multipart/form-data"),
//                    saveEcaEcp.docFile
//                )
//            )
//        }
//
//        builder1.addFormDataPart("studentid", saveEcaEcp.studentid)
//        builder1.addFormDataPart("ecpid", saveEcaEcp.ecpid)
//        builder1.addFormDataPart("topic", saveEcaEcp.topic)
//        builder1.addFormDataPart("fromdate", saveEcaEcp.fromdate)
//        builder1.addFormDataPart("todate", saveEcaEcp.todate)
//        builder1.addFormDataPart("certificatefrom", saveEcaEcp.certificatefrom)
//        builder1.addFormDataPart("description", saveEcaEcp.description)
//        builder1.addFormDataPart("learning", saveEcaEcp.learning)
//
//        val requestBody = builder1.build()
//        val call = builder.updateECPData(requestBody)
//        getResponse(call, responseCallback, context)
//    }


    private fun <T> getResponse(
        call: Call<T>,
        responseCallback: ResponseCallback?,
        context: Context?
    ) {

        try {

            if (context != null) {
                if (!Util.isNetworkAvailable(context)) {
                    Util.showError(context)
                    return
                }
            }

            if (context != null) {
                Util.showProgressDialog((context as Activity?)!!)
            }

            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (context != null) {
                        Util.dismissDialog()
                    }

                    if (responseCallback != null) {
                        if (response.body() != null) {
                            responseCallback.success(response.body()!!)
                        } else {
                            if (context != null) {
                                Util.showToast(
                                    "There may be some server or connection problem.",
                                    context
                                )
                            } else {
                                Util.showToast(
                                    "There may be some server or connection problem.",
                                    AppController.instance!!
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (context != null) {
                        Util.dismissDialog()
                        responseCallback?.failure(t)
                        if(call.isCanceled){
                            Util.showToast(
                                "Request Cancelled.", context)
                        }else {
                            Util.showToast(
                                "There may be some server or connection problem.", context)
                        }
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}