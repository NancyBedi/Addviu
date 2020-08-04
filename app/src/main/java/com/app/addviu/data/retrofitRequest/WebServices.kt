package com.app.smartboard.data.retrofitRequest


import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.DeleteCommonBean
import com.app.addviu.model.channelDetailBean.ChannelDetailBean
import com.app.addviu.model.channelVideoModel.ChannelVideoBean
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.TrendingBean
import com.app.addviu.model.latestVidModel.LatestVidBean
import com.app.addviu.model.notificationModel.NotificationBean
import com.app.addviu.model.playlistDetailBean.PlaylistDetailBean
import com.app.addviu.model.relatedModel.CommentsBean
import com.app.addviu.model.relatedModel.RelatedVideoBean
import com.app.addviu.model.userModel.SignInBean
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WebServices {

//    @GET("Getstate")
//    fun getStates(): Call<GetStateBean>

    @POST("home")
    fun getHomeVideoList(): Call<HomeBean>

    @GET("trendingVideos")
    fun getTrendingVideoList(@Query("page") pageId: Int): Call<TrendingBean>

    @FormUrlEncoded
    @POST("register")
    fun signUpUser(@FieldMap map: HashMap<String, String>): Call<SignUpBean>

    @FormUrlEncoded
    @POST("login")
    fun signInUser(@FieldMap map: HashMap<String, String>): Call<SignInBean>

    @FormUrlEncoded
    @POST("password/email")
    fun forgotPassword(@Field("email") email: String): Call<SignUpBean>

    @Multipart
    @POST("uploadVideo")
    fun uploadVideo(@Part video: MultipartBody.Part): Call<VideoUploadBean>

//    @Multipart
//    @POST("channel/createChannel")
//    fun addChannel(@Part image: MultipartBody.Part): Call<VideoUploadBean>

    @DELETE("deleteServerVideo")
    fun deleteVideo(@Query("original_name") originalName: String): Call<SignUpBean>

    @DELETE("deleteChannel/{channel_id}")
    fun deleteChannel(@Path("channel_id") channelId: String): Call<SignUpBean>

    @DELETE("deletePlaylist/{playlist_id}")
    fun deletePlaylist(@Path("playlist_id") playlist_id: String): Call<DeleteCommonBean>

    @POST("user/details")
    fun getUserDetails(): Call<TrendingBean>

    @POST("logout")
    fun logout(): Call<CommonSuccess>

    @GET("category")
    fun getCategories(): Call<CategoryBean>

    @GET("getNotifications")
    fun getNotifications(): Call<NotificationBean>

    @GET("clearNotifications")
    fun clearNotifications(): Call<NotificationBean>

    @GET("latesVideos")
    fun latesVideos(): Call<LatestVidBean>

    @GET("entertainmentAndComedyVideos")
    fun entertainmentAndComedyVideos(): Call<LatestVidBean>

    @GET("latestNewsVideos")
    fun latestNewsVideos(): Call<LatestVidBean>

    @GET("womenSpecialVideos")
    fun womenSpecialVideos(): Call<LatestVidBean>

    @GET("suggestedVideos")
    fun suggestedVideos(): Call<LatestVidBean>

    @FormUrlEncoded
    @POST("getUserChannels")
    fun getUserChannels(@Field("user_id") userId: Int): Call<ChannelBean>

    @FormUrlEncoded
    @POST("getChannelPlaylists")
    fun getChannelPlaylist(@Field("channel_id") channelId: Int): Call<PlaylistBean>

    @POST("uploadVideoData")
    fun uploadVideoData(@Body requestBody: RequestBody): Call<UploadDataBean>

    @POST("channel/createChannel")
    fun addChannel(@Body requestBody: RequestBody): Call<CommonSuccess>

    @POST("channel/updateChannel")
    fun updateChannel(@Body requestBody: RequestBody): Call<CommonSuccess>

    @POST("createPlaylist")
    fun createPlaylist(@Body requestBody: RequestBody): Call<CommonSuccess>

    @POST("updatePlaylist")
    fun updatePlaylist(@Body requestBody: RequestBody): Call<SignUpBean>

    @POST("updateChannelBanner/{channel_id}")
    fun updateChannelBanner(@Path("channel_id") channel_id: String, @Body requestBody: RequestBody): Call<SignUpBean>

    @POST("removeChannelBanner/{channel_id}")
    fun removeChannelBanner(@Path("channel_id") channel_id: String): Call<SignUpBean>

    @POST("updateChannelImage/{channel_id}")
    fun updateChannelImage(@Path("channel_id") channel_id: String, @Body requestBody: RequestBody): Call<SignUpBean>

    @POST("removeChannelImage/{channel_id}")
    fun removeChannelImage(@Path("channel_id") channel_id: String): Call<SignUpBean>

    @POST("updatePlaylistBanner/{playlist_id}")
    fun updatePlaylistBanner(@Path("playlist_id") playlist_id: String, @Body requestBody: RequestBody): Call<SignUpBean>

    @POST("removePlaylistBanner/{playlist_id}")
    fun removePlaylistBanner(@Path("playlist_id") playlist_id: String): Call<SignUpBean>

    @POST("updatePlaylistImage/{playlist_id}")
    fun updatePlaylistImage(@Path("playlist_id") playlist_id: String, @Body requestBody: RequestBody): Call<SignUpBean>

    @POST("removePlaylistImage/{playlist_id}")
    fun removePlaylistImage(@Path("playlist_id") playlist_id: String): Call<SignUpBean>

//    https://addviu.com/api/updatePlaylistBanner/{playlist_id}.

//    @Multipart
//    @POST("channel/createChannel")
//    fun addChannel(
//        @Part("channel_name") fname: RequestBody?,
//        @Part("category") lname: RequestBody?,
//        @Part("description") email: RequestBody?,
//        @Part file: MultipartBody.Part?
//    ): Call<VideoUploadBean>

    @GET("relatedVideos/{uid}")
    fun getRelatedVideos(@Path("uid") uid: String): Call<RelatedVideoBean>

    @GET("getComments/{uid}")
    fun getRelatedComments(@Path("uid") uid: String): Call<CommentsBean>

    @GET("getReplies/{comment_id}")
    fun getRelatedRelies(@Path("comment_id") commentId: String): Call<CommentsBean>

    @GET("channelPlaylists/{channel_id}")
    fun channelPlaylists(@Path("channel_id") channel_id: String): Call<PlaylistBean>

    @GET("channelVideos/{channel_id}")
    fun channelVideos(@Path("channel_id") channel_id: String): Call<ChannelVideoBean>

    @GET("channelDetails/{channel_id}")
    fun channelDetails(@Path("channel_id") channel_id: String): Call<ChannelDetailBean>

    @GET("playlistVideos/{playlist_id}")
    fun playlistVideos(@Path("playlist_id") playlist_id: String): Call<ChannelVideoBean>

    @GET("playlistDetails/{playlist_id}")
    fun playlistDetails(@Path("playlist_id") playlist_id: String): Call<PlaylistDetailBean>

    @POST("createComment/{uid}")
    fun createComment(@Path("uid") uid: String,@Body map: HashMap<String, String>): Call<SignUpBean>

    @POST("search")
    fun search(@Body map: HashMap<String, String>): Call<HomeBean>

    @POST("search")
    fun searchTrend(@Body map: HashMap<String, String>): Call<TrendingBean>

    @GET("userSubscribed/{channel_slug}")
    fun getSubscribeUser(@Path("channel_slug") channelSlug: String): Call<SubscribeBean>

    @POST("subscribeChannel/{channel_slug}")
    fun subscribeChannel(@Path("channel_slug") channelSlug: String): Call<SignUpBean>

    @GET("getVotes/{uid}")
    fun getVideoVotes(@Path("uid") uid: String): Call<VotesBean>

//    @GET("channelPlaylists/{channel_id}")
//    fun channelPlaylists(@Path("channel_id") channel_id: String): Call<VotesBean>

    @POST("setVote/{uid}")
    fun setVideoVotes(@Path("uid") uid: String,@Body map:HashMap<String,String>): Call<SignUpBean>

//    @POST("getUserChannels/{user_id}")
//    fun getUserChannels(@Path("user_id") uid: String): Call<SignUpBean>

    @DELETE("videos/{uid}/deletecomment/{comment_id}")
    fun deleteComment(@Path("uid") uid: String,@Path("comment_id") commentId: String): Call<SignUpBean>

//    @GET("Getcity")
//    fun getCity(@Query("stateid") stateid: Int): Call<GetCityBean>

//    @POST("StudentRegistration")
//    fun studentRegistration(@Body registerReqBean: RegisterReqBean): Call<RegisterBean>
//
//    @POST("PaymentStatus")
//    fun paymentStatus(@Body registerReqBean: PaymentApiBean): Call<RegisterBean>


}